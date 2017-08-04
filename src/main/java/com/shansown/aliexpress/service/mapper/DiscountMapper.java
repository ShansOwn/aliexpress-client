package com.shansown.aliexpress.service.mapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DiscountMapper implements ModelMapper<String, Integer> {

  private static final Pattern DISCOUNT_PATTERN = Pattern.compile("\\d+");

  @Override public Integer apply(String s) {
    if (StringUtils.isEmpty(s)) {
      return null;
    }
    Matcher matcher = DISCOUNT_PATTERN.matcher(s);
    return matcher.find()
        ? Integer.parseUnsignedInt(matcher.group())
        : null;
  }
}
