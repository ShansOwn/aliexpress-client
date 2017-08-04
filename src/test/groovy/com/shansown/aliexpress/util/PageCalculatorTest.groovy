package com.shansown.aliexpress.util

import spock.lang.Specification

class PageCalculatorTest extends Specification {

  def "next page should be calculated correctly"() {
    expect:
    PageCalculator.computeNextPage(curPage, totalResults, pageSize) == expectedPage

    where:
    curPage | totalResults | pageSize || expectedPage
    0       | 0            | 40       || OptionalInt.of(1)
    1       | 40           | 40       || OptionalInt.empty()
    1       | 41           | 40       || OptionalInt.of(2)
  }
}
