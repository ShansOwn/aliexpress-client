package com.shansown.aliexpress.api.request;

import com.shansown.aliexpress.config.properties.AliAccessProperty;
import lombok.Builder;

public class GetPromotionProductDetail extends BaseAliRequest {

  private static final String API_METHOD = "getPromotionProductDetail";

  @Builder
  public GetPromotionProductDetail(AliAccessProperty access, String fields, String productId,
      String localCurrency, String language) {
    super(access, API_METHOD);
    putParamIfPresent(FIELDS_KEY, fields);
    putParamIfPresent(PRODUCT_ID_KEY, productId);
    putParamIfPresent(LOCAL_CURRENCY_KEY, localCurrency);
    putParamIfPresent(LANGUAGE_KEY, language);
  }
}
