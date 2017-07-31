package com.shansown.aliexpress.api.request;

import com.shansown.aliexpress.config.properties.AliAccessProperty;
import lombok.Builder;

public class ListPromotionProductRequest extends BaseAliRequest {

  private static final String DEFAULT_FIELDS =
      "productUrl,imageUrl,originalPrice,salePrice,productId,productTitle,discount,validTime,commissionRate,commission";
  private static final String API_METHOD = "listPromotionProduct";

  @Builder
  public ListPromotionProductRequest(AliAccessProperty access, String fields, String keywords,
      String categoryId, Double originalPriceFrom, Double originalPriceTo, Integer volumeFrom,
      Integer volumeTo, Integer pageNo, Integer pageSize, String sort, Integer startCreditScore,
      Integer endCreditScore, String highQualityItems, String localCurrency, String language) {
    super(access, API_METHOD);
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
}
