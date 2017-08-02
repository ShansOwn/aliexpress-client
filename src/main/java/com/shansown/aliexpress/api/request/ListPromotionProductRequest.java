package com.shansown.aliexpress.api.request;

import com.shansown.aliexpress.api.error.ApiError;
import com.shansown.aliexpress.api.response.AliResponse;
import com.shansown.aliexpress.api.response.ListPromotionProductResult;
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

import static com.shansown.aliexpress.api.AliApi.CATEGORY_ID_KEY;
import static com.shansown.aliexpress.api.AliApi.END_CREDIT_CORE_KEY;
import static com.shansown.aliexpress.api.AliApi.FIELDS_KEY;
import static com.shansown.aliexpress.api.AliApi.HIGH_QUALITY_ITEMS_KEY;
import static com.shansown.aliexpress.api.AliApi.KWS_KEY;
import static com.shansown.aliexpress.api.AliApi.LANGUAGE_KEY;
import static com.shansown.aliexpress.api.AliApi.LOCAL_CURRENCY_KEY;
import static com.shansown.aliexpress.api.AliApi.ORIGINAL_PRICE_FROM_KEY;
import static com.shansown.aliexpress.api.AliApi.ORIGINAL_PRICE_TO_KEY;
import static com.shansown.aliexpress.api.AliApi.PAGE_NO_KEY;
import static com.shansown.aliexpress.api.AliApi.PAGE_SIZE_KEY;
import static com.shansown.aliexpress.api.AliApi.SORT_KEY;
import static com.shansown.aliexpress.api.AliApi.START_CREDIT_CORE_KEY;
import static com.shansown.aliexpress.api.AliApi.VOLUME_FROM_KEY;
import static com.shansown.aliexpress.api.AliApi.VOLUME_TO_KEY;
import static com.shansown.aliexpress.api.ApiMethod.LIST_PROMOTION_PRODUCT;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ListPromotionProductRequest extends BaseAliRequest<ListPromotionProductResult> {

  private static final ParameterizedTypeReference<AliResponse<ListPromotionProductResult>>
      RESULT_TYPE = new ParameterizedTypeReference<AliResponse<ListPromotionProductResult>>() {
  };

  @Builder
  public ListPromotionProductRequest(AliAccessProperty access, String fields, String keywords,
      String categoryId, Double originalPriceFrom, Double originalPriceTo, Integer volumeFrom,
      Integer volumeTo, Integer pageNo, Integer pageSize, String sort, Integer startCreditScore,
      Integer endCreditScore, String highQualityItems, String localCurrency, String language) {
    super(access, LIST_PROMOTION_PRODUCT);
    Objects.requireNonNull(fields, "Fields property required");
    Objects.requireNonNull(keywords, "Keywords property required");
    putParamIfPresent(FIELDS_KEY, fields);
    putParamIfPresent(KWS_KEY, keywords);
    putParamIfPresent(CATEGORY_ID_KEY, categoryId);
    putParamIfPresent(ORIGINAL_PRICE_FROM_KEY, originalPriceFrom);
    putParamIfPresent(ORIGINAL_PRICE_TO_KEY, originalPriceTo);
    putParamIfPresent(VOLUME_FROM_KEY, volumeFrom);
    putParamIfPresent(VOLUME_TO_KEY, volumeTo);
    putParamIfPresent(PAGE_NO_KEY, pageNo);
    putParamIfPresent(PAGE_SIZE_KEY, pageSize);
    putParamIfPresent(SORT_KEY, sort);
    putParamIfPresent(START_CREDIT_CORE_KEY, startCreditScore);
    putParamIfPresent(END_CREDIT_CORE_KEY, endCreditScore);
    putParamIfPresent(HIGH_QUALITY_ITEMS_KEY, highQualityItems);
    putParamIfPresent(LOCAL_CURRENCY_KEY, localCurrency);
    putParamIfPresent(LANGUAGE_KEY, language);
  }

  @Override
  public ParameterizedTypeReference<AliResponse<ListPromotionProductResult>> getResultType() {
    return RESULT_TYPE;
  }

  @Override
  public Optional<? extends ApiError> getErrorByCode(Long code) {
    return code != null
        ? Stream.of(Error.values()).filter(e -> e.getCode() == code).findAny()
        : Optional.empty();
  }

  @Getter
  @RequiredArgsConstructor
  private enum Error implements ApiError {
    SYSTEM(20020000, "System Error"),
    UNAUTHORIZED_REQUEST(20030000, "Unauthorized transfer request"),
    REQUIRED_PARAMETERS(20030010, "Required parameters"),
    INVALID_PROTOCOL(20030020, "Invalid protocol format"),
    API_VERSION(20030030, "API version input parameter error"),
    NAME_SPACE(20030040, "API name space input parameter error"),
    NAME(20030050, "API name space input parameter error"),
    FIELDS(20030060, "Fields input parameter error"),
    KEYWORD(20030070, "Keyword input parameter error"),
    CATEGORY_ID(20030080, "Category ID input parameter error"),
    TRACKING_ID(20030090, "Tracking ID input parameter error"),
    COMMISSION_RATE(20030100, "Commission rate input parameter error"),
    ORIGINAL_PRICE(20030110, "Original Price input parameter error"),
    DISCOUNT(20030120, "Discount input parameter error"),
    VOLUME(20030130, "Volume input parameter error"),
    PAGE_NUMBER(20030140, "Page number input parameter error"),
    PAGE_SIZE(20030150, "Page size input parameter error"),
    SORT(20030160, "Sort input parameter error"),
    CREDIT_SCORE(20030170, "Credit Score input parameter error");

    private final long code;
    private final String msg;
  }
}
