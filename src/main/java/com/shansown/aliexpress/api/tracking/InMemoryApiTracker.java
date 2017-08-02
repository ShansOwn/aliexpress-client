package com.shansown.aliexpress.api.tracking;

import com.shansown.aliexpress.api.AliApiMethod;
import com.shansown.aliexpress.api.request.AliRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class InMemoryApiTracker implements ApiTracker {

  private final Map<AliApiMethod, AtomicInteger> tracker = new HashMap<>();

  @Override
  public Mono<Boolean> track(AliRequest<?> request) {
    AliApiMethod method = request.getAliApiMethod();
    AtomicInteger counter = tracker.computeIfAbsent(method, m -> new AtomicInteger(0));
    return Mono.just(counter.incrementAndGet() <= method.getLimit());
  }
}
