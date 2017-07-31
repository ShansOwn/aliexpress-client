package com.shansown.aliexpress.controller;

import com.shansown.aliexpress.api.AliApi;
import com.shansown.aliexpress.api.request.AliRequest;
import com.shansown.aliexpress.api.request.ListPromotionProductRequest;
import com.shansown.aliexpress.config.properties.AliAccessProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RootController {

  private final AliAccessProperty aliAccessProperty;
  private final AliApi api;

  @GetMapping("/")
  public Mono<String> index() {
    AliRequest request = ListPromotionProductRequest.builder()
        .access(aliAccessProperty)
        .keywords("iphone 5s")
        .fields("productUrl,imageUrl,originalPrice,salePrice,productId")
        .build();
    return api.get(request).flatMap(r -> r.bodyToMono(String.class));
  }
}
