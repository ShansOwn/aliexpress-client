package com.shansown.aliexpress.service;

import com.shansown.aliexpress.api.AliApi;
import com.shansown.aliexpress.api.request.ListPromotionProductRequest;
import com.shansown.aliexpress.config.properties.AliAccessProperty;
import com.shansown.aliexpress.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductService {

  private final AliAccessProperty aliAccessProperty;
  private final AliApi api;

  public Flux<Product> findByKeyword() {
    return null;
  }

  private ListPromotionProductRequest createListProductRequest(String keyword) {
    return ListPromotionProductRequest.builder()
        .access(aliAccessProperty)
        .keywords(keyword)
        .fields(
            "productUrl,evaluateScore,imageUrl,allImageUrls,originalPrice,salePrice,productId,productTitle,discount,validTime,commissionRate,commission,volume")
        .build();
  }
}
