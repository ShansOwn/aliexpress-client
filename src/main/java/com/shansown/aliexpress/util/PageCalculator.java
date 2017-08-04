package com.shansown.aliexpress.util;

import java.util.OptionalInt;

public class PageCalculator {
  public static OptionalInt computeNextPage(int curPage, int totalResults, int pageSize) {
    if (curPage == 0) {
      return OptionalInt.of(1);
    }
    int allPages = (int) Math.ceil((double) totalResults / pageSize);
    return curPage < allPages
        ? OptionalInt.of(curPage + 1)
        : OptionalInt.empty();
  }
}
