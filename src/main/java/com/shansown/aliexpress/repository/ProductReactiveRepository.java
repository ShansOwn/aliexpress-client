package com.shansown.aliexpress.repository;

import com.shansown.aliexpress.model.Product;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

import static java.time.temporal.ChronoUnit.SECONDS;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductReactiveRepository {

  private final Scheduler dbScheduler;
  private final ProductRepository productRepository;

  public <S extends Product> Flux<S> saveAll(Publisher<S> entityStream) {
    return Flux.from(entityStream)
        .bufferTimeout(5_000, Duration.of(1, SECONDS))
        .map(productRepository::saveAll)
        .flatMap(Flux::fromIterable)
        .publishOn(dbScheduler);
  }
}
