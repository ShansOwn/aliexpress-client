package com.shansown.aliexpress.api.tracking;

import com.google.common.collect.ImmutableMap;
import com.shansown.aliexpress.api.AliApiMethod;
import com.shansown.aliexpress.api.request.AliRequest;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

@Primary
@Component
public class InMemoryApiTracker implements ApiTracker {

  private final Map<AliApiMethod, AtomicInteger> tracker =
      Stream.of(AliApiMethod.values())
          .collect(collectingAndThen(toMap(identity(), m -> new AtomicInteger(0)), ImmutableMap::copyOf));

  @Override
  public Mono<Boolean> track(AliRequest<?> request) {
    return Mono.just(request)
        .map(r -> {
          AliApiMethod method = request.getAliApiMethod();
          AtomicInteger counter = tracker.get(method);
          return counter.incrementAndGet() <= method.getLimit();
        });
  }

  @Override
  public Mono<Map<AliApiMethod, Integer>> getStatistics() {
    Map<AliApiMethod, Integer> statistics =
        tracker.entrySet().stream().collect(toMap(Map.Entry::getKey, e -> e.getValue().get()));
    return Mono.just(statistics);
  }

  @Override
  public Mono<Void> setStatistics(Map<AliApiMethod, Integer> statistics) {
    return Mono.just(statistics)
        .doOnSuccess(s -> s.forEach((k, v) -> tracker.get(k).set(v)))
        .then();
  }

  @Override
  public Mono<Void> resetStatistics() {
    return Mono.empty().doOnSuccess(__ -> tracker.forEach((k, v) -> v.set(0))).then();
  }
}
