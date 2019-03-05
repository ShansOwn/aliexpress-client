package com.shansown.aliexpress.api.response;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder")
public class AliProduct implements AliResult  {
  private final Long productId;
  private final String productTitle;
  private final String productUrl;
  private final String originalPrice;
  private final String salePrice;
  private final String discount;
  private final String imageUrl;
  private final String allImageUrls;
  private final Date validTime;
  private final Float evaluateScore;
  private final Integer volume;
}
