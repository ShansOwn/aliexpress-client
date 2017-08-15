package com.shansown.aliexpress.service.mapper;

import com.shansown.aliexpress.api.AliApiMethod;
import com.shansown.aliexpress.model.ApiTrack;
import java.util.Map.Entry;
import org.springframework.stereotype.Component;

@Component
public class ApiTrackMapper implements ModelMapper<Entry<AliApiMethod, Integer>, ApiTrack> {

  @Override
  public ApiTrack apply(Entry<AliApiMethod, Integer> entry) {
    return ApiTrack.builder()
        .method(entry.getKey().getMethodName())
        .requests(entry.getValue())
        .build();
  }
}
