package com.shansown.aliexpress.api.error;

import com.shansown.aliexpress.api.AliApiMethod;
import lombok.Getter;

@Getter
public class AliRequestLimitExceededException extends AliApiException {

  private final AliApiMethod method;

  public AliRequestLimitExceededException(AliApiMethod method) {
    super(String.format("Api method %s limit (%s) exceeded", method.getMethodName(), method.getLimit()));
    this.method = method;
  }
}
