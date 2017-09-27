package com.shansown.aliexpress.api;

import com.shansown.aliexpress.api.error.AliRequestException;
import com.shansown.aliexpress.api.request.AliRequest;
import com.shansown.aliexpress.api.response.AliResponse;
import com.shansown.aliexpress.api.response.AliResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.shansown.aliexpress.api.error.AliApiError.SUCCESS_CODE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebClientAliApi implements AliApi {

  private final WebClient webClient;

  public <T extends AliResult> Mono<AliResponse<T>> get(AliRequest<T> request) {
    String uri = request.toRequestString();
    log.trace("Invoke Ali api uri: {}", uri);
    return webClient.get().uri(uri).accept(APPLICATION_JSON).exchange()
        .flatMap(r -> r.bodyToMono(request.getResultType()))
        .flatMap(r -> r.getErrorCode() == SUCCESS_CODE
            ? Mono.just(r)
            : Mono.error(getError(request, r.getErrorCode())));
  }

  private AliRequestException getError(AliRequest<?> request, long errorCode) {
    return request.getErrorByCode(errorCode)
        .map(AliRequestException::new)
        .orElse(new AliRequestException(errorCode, "Unknown error"));
  }
}
