package com.shansown.aliexpress.service.mapper

import spock.lang.Specification

class PriceMapperTest extends Specification {

  def priceMapper = new PriceMapper()

  def "price should be mapped correctly"() {
    expect:
    priceMapper.apply(priceString) == price

    where:
    priceString || price
    "US \$2.99" || 2.99F
    "US \$1.00" || 1.0F
    "1.33"      || 1.33F
    "Not price" || null
  }
}
