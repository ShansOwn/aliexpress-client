package com.shansown.aliexpress.controller;

import com.shansown.aliexpress.model.Product;
import com.shansown.aliexpress.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

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

  @GetMapping("/get-updated")
  public Flux<Product> getUpdated(Date updatedFrom) {
    log.info("Get updated products from {}", updatedFrom);
    return productService.getUpdatedFrom(updatedFrom);
  }
}
