package com.shansown.aliexpress.api.request;

import com.shansown.aliexpress.api.error.AliApiError;
import com.shansown.aliexpress.api.response.AliProduct;
import com.shansown.aliexpress.api.response.AliResponse;
import com.shansown.aliexpress.config.properties.AliAccessProperty;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.core.ParameterizedTypeReference;

import static com.shansown.aliexpress.api.AliApi.FIELDS_KEY;
import static com.shansown.aliexpress.api.AliApi.LANGUAGE_KEY;
import static com.shansown.aliexpress.api.AliApi.LOCAL_CURRENCY_KEY;
import static com.shansown.aliexpress.api.AliApi.PRODUCT_ID_KEY;
import static com.shansown.aliexpress.api.AliApiMethod.GET_PROMOTION_PRODUCT_DETAIL;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class GetPromotionProductDetailRequest extends BaseAliRequest<AliProduct> {

  private static final ParameterizedTypeReference<AliResponse<AliProduct>>
      RESULT_TYPE = new ParameterizedTypeReference<AliResponse<AliProduct>>() {
  };

  @Builder
  public GetPromotionProductDetailRequest(AliAccessProperty access, String fields, Long productId,
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
  public ParameterizedTypeReference<AliResponse<AliProduct>> getResultType() {
    return RESULT_TYPE;
  }

  @Override
  public Optional<? extends AliApiError> getErrorByCode(Long code) {
    return code != null
        ? Stream.of(Error.values()).filter(e -> e.getCode() == code).findAny()
        : Optional.empty();
  }

  @Getter
  @RequiredArgsConstructor
  private enum Error implements AliApiError {
    SYSTEM(20020000, "System Error"),
    UNAUTHORIZED_REQUEST(20030000, "Unauthorized transfer request"),
    REQUIRED_PARAMETERS(20030010, "Required parameters"),
    INVALID_PROTOCOL(20030020, "Invalid protocol format"),
    API_VERSION(20030030, "API version input parameter error"),
    NAME_SPACE(20030040, "API name space input parameter error"),
    NAME(20030050, "API name space input parameter error"),
    FIELDS(20030060, "Fields input parameter error"),
    PRODUCT_ID(20030070, "Product ID input parameter error");

    private final long code;
    private final String msg;
  }
}
