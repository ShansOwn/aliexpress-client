package com.shansown.aliexpress.service.mapper

import spock.lang.Specification

class DiscountMapperTest extends Specification {

  def discountMapper = new DiscountMapper()

  def "discount should be mapped correctly"() {
    expect:
    discountMapper.apply(discountString) == discount

    where:
    discountString || discount
    "0%"           || 0
    "10%"          || 10
    "20"           || 20
    "Not discount" || null
  }
}
