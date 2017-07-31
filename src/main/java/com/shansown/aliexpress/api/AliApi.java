package com.shansown.aliexpress.api;

import com.shansown.aliexpress.api.request.AliRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

public interface AliApi {
  Mono<ClientResponse> get(AliRequest request);
}
