package com.shansown.aliexpress.api.response;

import java.util.List;
import lombok.Data;

@Data
public class ListPromotionProductResult implements AliResult {
  private final Long totalResults;
  private final List<AliProduct> products;
}
