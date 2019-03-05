package com.shansown.aliexpress.api;

import com.shansown.aliexpress.api.error.AliRequestLimitExceededException;
import com.shansown.aliexpress.api.request.AliRequest;
import com.shansown.aliexpress.api.response.AliResponse;
import com.shansown.aliexpress.api.response.AliResult;
import com.shansown.aliexpress.api.tracking.ApiTracker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Primary
@Component
@RequiredArgsConstructor
public class TrackedAliApi implements AliApi {

  private final AliApi aliApi;
  private final ApiTracker apiTracker;

  @Override
  public <T extends AliResult> Mono<AliResponse<T>> get(AliRequest<T> request) {
    return apiTracker.track(request)
        .flatMap(allowed -> allowed
            ? aliApi.get(request)
            : Mono.error(new AliRequestLimitExceededException(request.getAliApiMethod())));
  }
}
