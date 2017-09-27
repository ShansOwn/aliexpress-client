package com.shansown.aliexpress.service.mapper;

public interface JsonMapper<T, U extends Class<?>, R> {
  R apply(T t, U u);
}
