package com.shansown.aliexpress.controller;

import com.shansown.aliexpress.model.Product;
import com.shansown.aliexpress.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RootController {

  private final ProductService productService;

  @PostMapping("/find")
  public Mono<Long> find(@RequestBody String keyword) {
    Flux<Product> products = productService.requestByKeyword(keyword);
    return productService.saveAll(products);
  }
}
