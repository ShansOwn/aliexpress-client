package com.shansown.aliexpress.api.request;

import com.shansown.aliexpress.api.response.AliResult;
import com.shansown.aliexpress.config.properties.AliAccessProperty;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.shansown.aliexpress.api.AliApi.BASE_TEMPLATE;
import static com.shansown.aliexpress.api.AliApi.BASE_URL;

@ToString
@EqualsAndHashCode
public abstract class BaseAliRequest<T extends AliResult> implements AliRequest<T> {

  private final Map<String, String> params = new HashMap<>();
  private final AliAccessProperty access;
  private final String apiMethod;

  protected BaseAliRequest(AliAccessProperty access, String apiMethod) {
    Objects.requireNonNull(access, "Access property required");
    Objects.requireNonNull(apiMethod, "Api method required");
    this.access = access;
    this.apiMethod = apiMethod;
  }

  @Override
  public String toRequestString() {
    return baseUrl(access.getApiKey(), apiMethod) +
        params.entrySet().stream()
            .map(e -> String.format("%s=%s", e.getKey(), e.getValue()))
            .collect(Collectors.joining("&"));
  }

  String baseUrl(String apiKey, String apiMethod) {
    return String.format(BASE_TEMPLATE, BASE_URL, apiMethod, apiKey);
  }

  void putParamIfPresent(String param, Number value) {
    if (Objects.nonNull(value)) {
      params.put(param, String.valueOf(value));
    }
  }

  void putParamIfPresent(String param, String value) {
    if (Objects.nonNull(value)) {
      params.put(param, value);
    }
  }
}
