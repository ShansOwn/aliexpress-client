package com.shansown.aliexpress.api.tracking;

import com.shansown.aliexpress.api.request.AliRequest;
import reactor.core.publisher.Mono;

public interface ApiTracker {
  /**
   * Tracks request and returns whether it's allowed to be processed
   *
   * @param request request to perform
   * @return a Mono with the allowance result
   */
  Mono<Boolean> track(AliRequest<?> request);
}
