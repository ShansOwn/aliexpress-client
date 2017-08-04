package com.shansown.aliexpress.repository;

import com.shansown.aliexpress.model.Category;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CategoryReactiveRepository implements ReactiveCrudRepository<Category, Long> {

  private final Scheduler dbScheduler;
  private final CategoryRepository categoryRepository;

  @Override
  public <S extends Category> Mono<S> save(S entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <S extends Category> Flux<S> saveAll(Iterable<S> entities) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <S extends Category> Flux<S> saveAll(Publisher<S> entityStream) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Category> findById(Long id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Category> findById(Publisher<Long> id) {
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
  @Cacheable("categories")
  public Flux<Category> findAll() {
    return Flux.fromIterable(categoryRepository.findAll()).publishOn(dbScheduler);
  }

  @Override
  public Flux<Category> findAllById(Iterable<Long> ids) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Flux<Category> findAllById(Publisher<Long> idStream) {
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
  public Mono<Void> delete(Category entity) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Void> deleteAll(Iterable<? extends Category> entities) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Void> deleteAll(Publisher<? extends Category> entityStream) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Mono<Void> deleteAll() {
    throw new UnsupportedOperationException();
  }
}
