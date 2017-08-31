package com.shansown.aliexpress.repository;

import com.shansown.aliexpress.model.Product;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Collections.emptySet;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductReactiveRepository {

  private final Scheduler dbScheduler;
  private final ProductRepository productRepository;

  public <S extends Product> Flux<S> mergeAll(Publisher<S> entityStream) {
    return Flux.from(entityStream)
        .bufferTimeout(5_000, Duration.of(1, SECONDS))
        .map(ps -> {
          Iterable<Product> products = productRepository.findAllById(ps.stream().map(Product::getId).collect(toList()));
          Map<Long, Set<Long>> cats = StreamSupport.stream(products.spliterator(), false)
              .collect(toMap(Product::getId, Product::getCategoryIds));
          ps.forEach(p -> p.getCategoryIds().addAll(cats.computeIfAbsent(p.getId(), __ -> emptySet())));
          return ps;
        })
        .map(productRepository::saveAll)
        .flatMap(Flux::fromIterable)
        .publishOn(dbScheduler);
  }
}
