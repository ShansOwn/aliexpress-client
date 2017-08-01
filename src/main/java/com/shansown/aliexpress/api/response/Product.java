package com.shansown.aliexpress.api.response;

import java.util.Date;
import lombok.Data;

@Data
public class Product {
  private final String productId;
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
