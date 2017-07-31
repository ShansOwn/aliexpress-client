package com.shansown.aliexpress.api;

import com.shansown.aliexpress.api.request.AliRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebClientAliApi implements AliApi {

  private final WebClient webClient;

  @Override
  public Mono<ClientResponse> get(AliRequest request) {
    return webClient.get().uri(request.toRequestString()).accept(APPLICATION_JSON).exchange();
  }
}
