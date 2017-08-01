package com.shansown.aliexpress.api.request;

import com.shansown.aliexpress.api.response.AliResponse;
import com.shansown.aliexpress.api.response.GetPromotionProductDetailResult;
import com.shansown.aliexpress.config.properties.AliAccessProperty;
import java.util.Objects;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.core.ParameterizedTypeReference;

import static com.shansown.aliexpress.api.AliApi.FIELDS_KEY;
import static com.shansown.aliexpress.api.AliApi.LANGUAGE_KEY;
import static com.shansown.aliexpress.api.AliApi.LOCAL_CURRENCY_KEY;
import static com.shansown.aliexpress.api.AliApi.PRODUCT_ID_KEY;
import static com.shansown.aliexpress.api.ApiMethod.GET_PROMOTION_PRODUCT_DETAIL;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GetPromotionProductDetailRequest extends BaseAliRequest<GetPromotionProductDetailResult> {

  private static final ParameterizedTypeReference<AliResponse<GetPromotionProductDetailResult>>
      RESULT_TYPE = new ParameterizedTypeReference<AliResponse<GetPromotionProductDetailResult>>() {
  };

  @Builder
  public GetPromotionProductDetailRequest(AliAccessProperty access, String fields, String productId,
      String localCurrency, String language) {
    super(access, GET_PROMOTION_PRODUCT_DETAIL);
    Objects.requireNonNull(fields, "Fields property required");
    Objects.requireNonNull(productId, "Product Id property required");
    putParamIfPresent(FIELDS_KEY, fields);
    putParamIfPresent(PRODUCT_ID_KEY, productId);
    putParamIfPresent(LOCAL_CURRENCY_KEY, localCurrency);
    putParamIfPresent(LANGUAGE_KEY, language);
  }

  @Override
  public ParameterizedTypeReference<AliResponse<GetPromotionProductDetailResult>> getResultType() {
    return RESULT_TYPE;
  }
}
