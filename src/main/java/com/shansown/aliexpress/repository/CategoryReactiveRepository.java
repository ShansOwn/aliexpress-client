package com.shansown.aliexpress.repository;

import com.shansown.aliexpress.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

@Repository
@RequiredArgsConstructor
public class CategoryReactiveRepository {

  private final Scheduler dbScheduler;
  private final CategoryRepository categoryRepository;

  @Cacheable("categories")
  public Flux<Category> findAll() {
    return Flux.fromIterable(categoryRepository.findAll()).publishOn(dbScheduler);
  }
}
