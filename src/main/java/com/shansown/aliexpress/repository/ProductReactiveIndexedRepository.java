package com.shansown.aliexpress.repository;

import com.shansown.aliexpress.model.Product;
import com.shansown.aliexpress.repository.search.ProductSearch;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductReactiveIndexedRepository {

  @Value("${com.shansown.aliexpress.general.batch}")
  private int batch;

  private final Scheduler dbScheduler;
  private final ProductRepository productRepository;
  private final ProductSearch productSearch;

  public Flux<Product> mergeAll(Publisher<Product> entityStream) {
    return Flux.from(entityStream)
        .bufferTimeout(batch, Duration.of(1, SECONDS))
        .map(ps -> {
          final Date updated = new Date();
          Iterable<Product> products = productRepository.findAllById(ps.stream().map(Product::getId).collect(toList()));
          Map<Long, Set<Long>> cats = StreamSupport.stream(products.spliterator(), false)
              .collect(toMap(Product::getId, Product::getCategoryIds));
          ps.forEach(p -> {
            p.setUpdated(updated);
            p.getCategoryIds().addAll(cats.computeIfAbsent(p.getId(), __ -> emptySet()));
          });
          return ps;
        })
        .publishOn(dbScheduler)
        .map(productRepository::saveAll)
        .doOnNext(productSearch::index)
        .flatMap(Flux::fromIterable);
  }

  public Flux<Product> reindexAll() {
    return Flux.fromIterable(productRepository.findAll())
        .bufferTimeout(batch, Duration.of(1, SECONDS))
        .doOnNext(productSearch::index)
        .flatMap(Flux::fromIterable);
  }
}
