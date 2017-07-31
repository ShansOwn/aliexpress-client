package com.shansown.aliexpress.api.request;

import com.shansown.aliexpress.config.properties.AliAccessProperty;
import lombok.Builder;

public class GetPromotionLinks extends BaseAliRequest {

  private static final String API_METHOD = "getPromotionLinks";

  @Builder
  private GetPromotionLinks(AliAccessProperty access, String fields, String urls) {
    super(access, API_METHOD);
    putParamIfPresent(FIELDS_KEY, fields);
    putParamIfPresent(TRACKING_ID_KEY, access.getTrackingId());
    putParamIfPresent(URLS_KEY, urls);
  }
}
