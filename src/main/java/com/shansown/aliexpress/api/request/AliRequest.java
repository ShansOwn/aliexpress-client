package com.shansown.aliexpress.api.request;

import com.shansown.aliexpress.api.ApiMethod;
import com.shansown.aliexpress.api.error.ApiError;
import com.shansown.aliexpress.api.response.AliResponse;
import com.shansown.aliexpress.api.response.AliResult;
import java.util.Optional;
import org.springframework.core.ParameterizedTypeReference;

public interface AliRequest<T extends AliResult> {

  String toRequestString();

  ParameterizedTypeReference<AliResponse<T>> getResultType();

  ApiMethod getApiMethod();

  Optional<? extends ApiError> getErrorByCode(Long code);
}
