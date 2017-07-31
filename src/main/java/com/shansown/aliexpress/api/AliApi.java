package com.shansown.aliexpress.api;

import com.shansown.aliexpress.api.request.AliRequest;
import com.shansown.aliexpress.api.response.AliResponse;
import com.shansown.aliexpress.api.response.AliResult;
import reactor.core.publisher.Mono;

public interface AliApi {

  String BASE_URL = "http://gw.api.alibaba.com/openapi/param2/2/portals.open";
  String BASE_TEMPLATE = "%s/api.%s/%s?";

  // Fields
  String FIELDS_KEY = "fields";
  String KWS_KEY = "keywords";
  String CATEGORY_ID_KEY = "categoryId";
  String PRODUCT_ID_KEY = "productId";
  String TRACKING_ID_KEY = "trackingId";
  String URLS_KEY = "urls";
  String ORIGINAL_PRICE_FROM_KEY = "originalPriceFrom";
  String ORIGINAL_PRICE_TO_KEY = "originalPriceTo";
  String VOLUME_FROM_KEY = "volumeFrom";
  String VOLUME_TO_KEY = "volumeTo";
  String PAGE_NO_KEY = "pageNo";
  String PAGE_SIZE_KEY = "pageSize";
  String SORT_KEY = "sort";
  String START_CREDIT_CORE_KEY = "startCreditScore";
  String END_CREDIT_CORE_KEY = "endCreditScore";
  String HIGH_QUALITY_ITEMS_KEY = "highQualityItems";
  String LOCAL_CURRENCY_KEY = "localCurrency";
  String LANGUAGE_KEY = "language";

  <T extends AliResult> Mono<AliResponse<T>> get(AliRequest<T> request);
}
