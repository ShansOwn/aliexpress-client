package com.shansown.aliexpress.repository.specification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class ProductForSaleSpecification extends ProductsByValidAndUpdatedTimeSpecification {

  @Value("${com.shansown.aliexpress.general.product-valid-hours}")
  private int productValidHours;

  protected Date minimumUpdatedTime() {
    LocalDateTime oldestValid = LocalDateTime.now().minusHours(productValidHours);
    return Date.from(oldestValid.atZone(ZoneId.systemDefault()).toInstant());
  }
}
