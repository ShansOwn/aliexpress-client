package com.shansown.aliexpress.controller;

import com.shansown.aliexpress.api.AliApi;
import com.shansown.aliexpress.api.request.AliRequest;
import com.shansown.aliexpress.api.request.GetPromotionLinks;
import com.shansown.aliexpress.api.request.GetPromotionProductDetail;
import com.shansown.aliexpress.api.request.ListPromotionProductRequest;
import com.shansown.aliexpress.config.properties.AliAccessProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RootController {

  private final AliAccessProperty aliAccessProperty;
  private final AliApi api;

  @GetMapping("/list")
  public Mono<String> list() {
    AliRequest request = ListPromotionProductRequest.builder()
        .access(aliAccessProperty)
        .keywords("iphone 5s")
        .fields("productUrl,imageUrl,originalPrice,salePrice,productId")
        .build();
    return makeRequest(request);
  }

  @GetMapping("/details")
  public Mono<String> details() {
    AliRequest request = GetPromotionProductDetail.builder()
        .access(aliAccessProperty)
        .productId("32673619670")
        .fields("productUrl,imageUrl,originalPrice,salePrice,productId")
        .build();
    return makeRequest(request);
  }

  @GetMapping("/links")
  public Mono<String> links() {
    AliRequest request = GetPromotionLinks.builder()
        .access(aliAccessProperty)
        .urls("https://www.aliexpress.com/item/5S-Factory-Unlocked-Original-Apple-iPhone-5S-16GB-32GB-64GB-ROM-8MP-Touch-ID-iCloud-App/32673619670.html")
        .fields("promotionUrl")
        .build();
    return makeRequest(request);
  }

  private Mono<String> makeRequest(AliRequest request) {
    return api.get(request).flatMap(r -> r.bodyToMono(String.class));
  }
}
