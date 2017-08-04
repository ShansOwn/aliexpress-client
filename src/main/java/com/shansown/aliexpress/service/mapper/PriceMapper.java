package com.shansown.aliexpress.service.mapper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class PriceMapper implements ModelMapper<String, Float> {

  private static final Pattern PRICE_PATTERN = Pattern.compile("\\d+\\.\\d+");

  @Override
  public Float apply(String s) {
    if (StringUtils.isEmpty(s)) {
      return null;
    }
    Matcher matcher = PRICE_PATTERN.matcher(s);
    return matcher.find()
        ? Float.parseFloat(matcher.group())
        : null;
  }
}
