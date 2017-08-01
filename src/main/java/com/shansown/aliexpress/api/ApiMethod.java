package com.shansown.aliexpress.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum  ApiMethod {
  LIST_PROMOTION_PRODUCT("listPromotionProduct", 300_000),
  GET_PROMOTION_PRODUCT_DETAIL("getPromotionProductDetail", 100_000),
  GET_PROMOTION_LINKS("getPromotionLinks", 50_000);

  private final String methodName;
  private final Integer threshold;
}
