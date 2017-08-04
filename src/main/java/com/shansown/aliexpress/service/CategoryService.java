package com.shansown.aliexpress.service;

import com.shansown.aliexpress.model.Category;
import com.shansown.aliexpress.repository.CategoryReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CategoryService {

  private final CategoryReactiveRepository categoryRepository;

  public Flux<Category> getAll() {
    return categoryRepository.findAll();
  }
}
