package com.shansown.aliexpress.service.mapper;

public interface ModelMapper<T, R> {
  R apply(T t);
}
