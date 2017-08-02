package com.shansown.aliexpress.controller;

import com.shansown.aliexpress.api.AliApi;
import com.shansown.aliexpress.api.error.AliApiException;
import com.shansown.aliexpress.api.error.AliRequestException;
import com.shansown.aliexpress.api.error.AliRequestLimitExceededException;
import com.shansown.aliexpress.api.request.AliRequest;
import com.shansown.aliexpress.api.request.GetPromotionLinksRequest;
import com.shansown.aliexpress.api.request.GetPromotionProductDetailRequest;
import com.shansown.aliexpress.api.request.ListPromotionProductRequest;
import com.shansown.aliexpress.api.response.AliResponse;
import com.shansown.aliexpress.api.response.AliResult;
import com.shansown.aliexpress.api.response.GetPromotionLinksResult;
import com.shansown.aliexpress.api.response.GetPromotionProductDetailResult;
import com.shansown.aliexpress.api.response.ListPromotionProductResult;
import com.shansown.aliexpress.config.properties.AliAccessProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RootController {

  private final AliAccessProperty aliAccessProperty;
  private final AliApi api;

  @GetMapping("/list")
  public Mono<ListPromotionProductResult> list() {
    ListPromotionProductRequest request = ListPromotionProductRequest.builder()
        .access(aliAccessProperty)
        .keywords("iphone 6s")
        .fields(
            "productUrl,evaluateScore,imageUrl,allImageUrls,originalPrice,salePrice,productId,productTitle,discount,validTime,commissionRate,commission,volume")
        .build();
    return doRequest(request);
  }

  @GetMapping("/details")
  public Mono<GetPromotionProductDetailResult> details() {
    GetPromotionProductDetailRequest request = GetPromotionProductDetailRequest.builder()
        .access(aliAccessProperty)
        .productId("32673619670")
        .fields("productUrl,imageUrl,originalPrice,salePrice,productId")
        .build();
    return doRequest(request);
  }

  @GetMapping("/links")
  public Mono<GetPromotionLinksResult> links() {
    GetPromotionLinksRequest request = GetPromotionLinksRequest.builder()
        .access(aliAccessProperty)
        .urls(
            "https://www.aliexpress.com/item/5S-Factory-Unlocked-Original-Apple-iPhone-5S-16GB-32GB-64GB-ROM-8MP-Touch-ID-iCloud-App/32673619670.html")
        .fields("promotionUrl")
        .build();
    return doRequest(request);
  }

  private <T extends AliResult> Mono<T> doRequest(AliRequest<T> request) {
    return api.get(request)
        .doOnNext(r -> log.debug("Response: {}", r))
        .doOnError(AliRequestException.class, e -> log.warn("Ali request error: {}, {}", e.getCode(), e.getMsg()))
        .doOnError(AliRequestLimitExceededException.class,
            e -> log.warn("Ali request limit ({}) for method {} exceeded",
                e.getMethod().getLimit(), e.getMethod().getMethodName()))
        .doOnError(e -> !(e instanceof AliApiException), e -> log.error("Unexpected error", e))
        .map(AliResponse::getResult);
  }
}
