package com.shansown.aliexpress.repository;

import com.shansown.aliexpress.model.Product;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import static java.time.temporal.ChronoUnit.SECONDS;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductReactiveRepository implements ReactiveCrudRepository<Product, Long> {

  private final Scheduler dbScheduler;
  private final ProductRepository productRepository;

  @Override
  public <S extends Product> Mono<S> save(S entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <S extends Product> Flux<S> saveAll(Iterable<S> entities) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <S extends Product> Flux<S> saveAll(Publisher<S> entityStream) {
    return Flux.from(entityStream)
        .bufferTimeout(5_000, Duration.of(1, SECONDS))
        .map(productRepository::saveAll)
        .flatMap(Flux::fromIterable)
        .publishOn(dbScheduler);
  }

  @Override
  public Mono<Product> findById(Long id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Product> findById(Publisher<Long> id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Boolean> existsById(Long id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Boolean> existsById(Publisher<Long> id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Flux<Product> findAll() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Flux<Product> findAllById(Iterable<Long> longs) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Flux<Product> findAllById(Publisher<Long> idStream) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Long> count() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Void> deleteById(Long id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Void> deleteById(Publisher<Long> id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Void> delete(Product entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Void> deleteAll(Iterable<? extends Product> entities) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Void> deleteAll(Publisher<? extends Product> entityStream) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Void> deleteAll() {
    throw new UnsupportedOperationException();
  }
}
