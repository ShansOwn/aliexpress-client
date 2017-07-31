package com.shansown.aliexpress.api.response;

import lombok.Data;

@Data
public class GetPromotionProductDetailResult implements AliResult {
  private final String allImageUrls;
  private final String productUrl;
  private final String productId;
}
