package com.shansown.aliexpress.api.error;

import lombok.Getter;

@Getter
public class AliRequestException extends AliApiException {

  private final long code;
  private final String msg;

  public AliRequestException(AliApiError aliApiError) {
    this(aliApiError.getCode(), aliApiError.getMsg());
  }

  public AliRequestException(long code, String msg) {
    super(String.format("Code: %s, msg: %s", code, msg));
    this.code = code;
    this.msg = msg;
  }
}
