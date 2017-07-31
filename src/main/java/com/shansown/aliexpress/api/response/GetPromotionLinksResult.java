package com.shansown.aliexpress.api.response;

import java.util.List;
import lombok.Data;

@Data
public class GetPromotionLinksResult implements AliResult {
  private final List<PromotionLink> promotionUrls;
}
