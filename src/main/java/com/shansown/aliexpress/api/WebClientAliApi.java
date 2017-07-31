package com.shansown.aliexpress.api;

import com.shansown.aliexpress.api.request.AliRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebClientAliApi implements AliApi {

  private final WebClient webClient;

  @Override
  public Mono<ClientResponse> get(AliRequest request) {
    String uri = request.toRequestString();
    log.info("Invoke Ali api uri: {}", uri);
    return webClient.get().uri(uri).accept(APPLICATION_JSON).exchange();
  }
}
