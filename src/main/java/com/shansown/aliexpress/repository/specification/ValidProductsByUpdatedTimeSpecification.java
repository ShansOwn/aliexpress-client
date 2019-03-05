package com.shansown.aliexpress.repository.specification;

import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor
public class ValidProductsByUpdatedTimeSpecification extends ProductsByValidAndUpdatedTimeSpecification {

  private final Date updatedFrom;

  @Override
  protected Date minimumUpdatedTime() {
    return updatedFrom;
  }
}
