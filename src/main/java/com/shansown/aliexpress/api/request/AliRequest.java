package com.shansown.aliexpress.api.request;

import com.shansown.aliexpress.api.AliApiMethod;
import com.shansown.aliexpress.api.error.AliApiError;
import com.shansown.aliexpress.api.response.AliResponse;
import com.shansown.aliexpress.api.response.AliResult;
import java.util.Optional;
import org.springframework.core.ParameterizedTypeReference;

public interface AliRequest<T extends AliResult> {

  String toRequestString();

  ParameterizedTypeReference<AliResponse<T>> getResultType();

  AliApiMethod getAliApiMethod();

  Optional<? extends AliApiError> getErrorByCode(Long code);
}
