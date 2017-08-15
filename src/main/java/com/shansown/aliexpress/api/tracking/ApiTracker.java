package com.shansown.aliexpress.api.tracking;

import com.shansown.aliexpress.api.AliApiMethod;
import com.shansown.aliexpress.api.request.AliRequest;
import java.util.Map;
import reactor.core.publisher.Mono;

public interface ApiTracker {
  /**
   * Tracks request and returns whether it's allowed to be processed
   *
   * @param request request to perform
   * @return a Mono with the allowance result
   */
  Mono<Boolean> track(AliRequest<?> request);

  /**
   * Returns immutable map of collected statistic of api method invocations since last {@link #reset}
   *
   * @return immutable map of statistic
   */
  Mono<Map<AliApiMethod, Integer>> getStatistics();

  /**
   * Sets statistic. May be used for initialization during startup
   *
   * @param statistic of api method invocations
   * @return void
   */
  Mono<Void> setStatistics(Map<AliApiMethod, Integer> statistic);

  /**
   * Resets collected statistics
   *
   * @return void
   */
  Mono<Void> resetStatistics();
}
