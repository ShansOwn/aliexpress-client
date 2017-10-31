package com.shansown.aliexpress.controller;

import com.shansown.aliexpress.model.Product;
import com.shansown.aliexpress.service.ProductService;
import java.util.Collection;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RootController {

  private final ProductService productService;

  @PostMapping("/find")
  public Mono<Long> find(@RequestBody String keyword) {
    log.info("Find products by keyword: '{}'", keyword);
    Flux<Product> products = productService.requestByKeyword(keyword);
    return productService.saveAll(products)
        .doOnSuccess(count -> log.info("{} products found and processed with '{}' keyword", count, keyword));
  }

  @PostMapping("/details")
  public Mono<Long> details(@RequestBody Collection<Long> ids) {
    log.info("Find products by ids: {}", ids.size());
    Flux<Product> products = Flux.fromIterable(new HashSet<>(ids))
        .flatMap(productService::requestDetails);
    return productService.saveAll(products)
        .doOnSuccess(count -> log.info("{} products found and processed with {} ids", count, ids.size()));
  }

  @GetMapping("/get-for-sale")
  public Flux<Product> getForSale() {
    log.info("Get products for sale");
    return productService.getForSale();
  }
}
