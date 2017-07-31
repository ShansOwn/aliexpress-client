package com.shansown.aliexpress.api.request;

import com.shansown.aliexpress.config.properties.AliAccessProperty;
import java.util.Objects;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.shansown.aliexpress.api.AliApi.FIELDS_KEY;
import static com.shansown.aliexpress.api.AliApi.LANGUAGE_KEY;
import static com.shansown.aliexpress.api.AliApi.LOCAL_CURRENCY_KEY;
import static com.shansown.aliexpress.api.AliApi.PRODUCT_ID_KEY;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GetPromotionProductDetail extends BaseAliRequest {

  private static final String API_METHOD = "getPromotionProductDetail";

  @Builder
  public GetPromotionProductDetail(AliAccessProperty access, String fields, String productId,
      String localCurrency, String language) {
    super(access, API_METHOD);
    Objects.requireNonNull(fields, "Fields property required");
    Objects.requireNonNull(productId, "Product Id property required");
    putParamIfPresent(FIELDS_KEY, fields);
    putParamIfPresent(PRODUCT_ID_KEY, productId);
    putParamIfPresent(LOCAL_CURRENCY_KEY, localCurrency);
    putParamIfPresent(LANGUAGE_KEY, language);
  }
}
