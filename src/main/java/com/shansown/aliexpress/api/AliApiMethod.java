package com.shansown.aliexpress.api;

import java.util.Objects;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AliApiMethod {

  LIST_PROMOTION_PRODUCT("listPromotionProduct", 300_000),
  GET_PROMOTION_PRODUCT_DETAIL("getPromotionProductDetail", 100_000),
  GET_PROMOTION_LINKS("getPromotionLinks", 50_000);

  public static AliApiMethod byMethodName(String methodName) {
    return Stream.of(values())
        .filter(method -> Objects.equals(method.getMethodName(), methodName))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("Unknown method name: " + methodName));
  }

  private final String methodName;
  private final int limit;
}
