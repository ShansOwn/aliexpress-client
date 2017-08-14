package com.shansown.aliexpress.util;

import java.util.OptionalInt;

import static com.shansown.aliexpress.api.AliApi.MAX_PAGE_SIZE;

public class PageCalculator {

  public static OptionalInt computeNextPage(int curPage, long totalResults, int pageSize) {
    if (curPage == 0) {
      return OptionalInt.of(1);
    }
    int allPages = computePagesNumber(totalResults, pageSize);
    return curPage < allPages
        ? OptionalInt.of(curPage + 1)
        : OptionalInt.empty();
  }

  public static int computePagesNumber(long totalResults) {
    return computePagesNumber(totalResults, MAX_PAGE_SIZE);
  }

  public static int computePagesNumber(long totalResults, int pageSize) {
    return (int) Math.ceil((double) totalResults / pageSize);
  }
}
