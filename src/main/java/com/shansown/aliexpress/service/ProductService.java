package com.shansown.aliexpress.service;

import com.shansown.aliexpress.api.AliApi;
import com.shansown.aliexpress.api.error.AliRequestException;
import com.shansown.aliexpress.api.request.GetPromotionLinksRequest;
import com.shansown.aliexpress.api.request.GetPromotionLinksRequest.GetPromotionLinksRequestBuilder;
import com.shansown.aliexpress.api.request.ListPromotionProductRequest;
import com.shansown.aliexpress.api.request.ListPromotionProductRequest.ListPromotionProductRequestBuilder;
import com.shansown.aliexpress.api.response.*;
import com.shansown.aliexpress.config.properties.AliAccessProperty;
import com.shansown.aliexpress.model.Category;
import com.shansown.aliexpress.model.Product;
import com.shansown.aliexpress.repository.ProductReactiveRepository;
import com.shansown.aliexpress.service.mapper.ProductMapper;
import com.shansown.aliexpress.util.PageCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.shansown.aliexpress.api.AliApi.MAX_LINK_URLS;
import static com.shansown.aliexpress.api.AliApi.MAX_PAGE_SIZE;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductService {

  private final AliAccessProperty aliAccessProperty;
  private final AliApi api;

  private final CategoryService categoryService;

  private final ProductMapper productMapper;

  private final ProductReactiveRepository productRepository;

  public Flux<Product> requestByKeyword(String keyword) {
    log.debug("Find by keyword: {}", keyword);
    return categoryService.getAll()
        .map(Category::getId)
        .flatMap(catId -> requestAllAliProducts(catId, keyword)
            .buffer(MAX_LINK_URLS)
            .flatMap(products -> requestPromotionLinks(products)
                .map(urls -> mapProducts(catId, products, urls))))
        .flatMap(Flux::fromStream);
  }

  @Transactional
  public Mono<Long> saveAll(Flux<Product> products) {
    log.debug("Save all");
    return productRepository.saveAll(products).count();
  }

  private Flux<AliProduct> requestAllAliProducts(Long catId, String keyword) {
    return requestAliProductsPages(catId, keyword)
        .flatMap(page -> requestListAliProducts(catId, keyword, page))
        .filter(r -> r.getProducts().size() > 0)
        .map(ListPromotionProductResult::getProducts)
        .flatMap(Flux::fromIterable);
  }

  private Flux<Integer> requestAliProductsPages(Long catId, String keyword) {
    return requestListAliProducts(catId, keyword, null)
        .flatMap(r -> Flux.range(1, PageCalculator.computePagesNumber(r.getTotalResults()) + 1));
  }

  private Flux<ListPromotionProductResult> requestListAliProducts(Long catId, String keyword, @Nullable Integer page) {
    return Flux.just(createListProductRequestBuilder(keyword).categoryId(catId).pageNo(page).build())
        .flatMap(api::get)
        .onErrorResume(AliRequestException.class, e -> Flux.empty())
        .map(AliResponse::getResult);
  }

  private Flux<List<PromotionLink>> requestPromotionLinks(List<AliProduct> products) {
    return Flux.just(createPromotionLinksRequestBuilder(products).build())
        .flatMap(api::get)
        .map(AliResponse::getResult)
        .map(GetPromotionLinksResult::getPromotionUrls);
  }

  private ListPromotionProductRequestBuilder createListProductRequestBuilder(String keyword) {
    return ListPromotionProductRequest.builder()
        .access(aliAccessProperty)
        .keywords(keyword)
        .pageSize(MAX_PAGE_SIZE)
        .fields("productUrl,evaluateScore,imageUrl,originalPrice,salePrice,productId,productTitle,discount,validTime");
  }

  private GetPromotionLinksRequestBuilder createPromotionLinksRequestBuilder(Collection<AliProduct> products) {
    String urls = products.stream().map(AliProduct::getProductUrl).collect(joining(","));
    return GetPromotionLinksRequest.builder()
        .access(aliAccessProperty)
        .fields("promotionUrl")
        .urls(urls);
  }

  private Stream<Product> mapProducts(Long catId, List<AliProduct> products, List<PromotionLink> promotionUrls) {
    Map<String, String> promotionLinksByUrl = promotionUrls.stream()
        .collect(toMap(PromotionLink::getUrl, PromotionLink::getPromotionUrl));
    return products.stream()
        .map(productMapper)
        .peek(product -> {
          product.setCategoryId(catId);
          product.setPromotionUrl(promotionLinksByUrl.get(product.getProductUrl()));
        });
  }
}
