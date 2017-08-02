package com.shansown.aliexpress.api.error;

import lombok.Getter;

@Getter
public class AliRequestError extends RuntimeException {

  private final long code;
  private final String msg;

  public AliRequestError(ApiError apiError) {
    this(apiError.getCode(), apiError.getMsg());
  }

  public AliRequestError(long code, String msg) {
    super(msg);
    this.code = code;
    this.msg = msg;
  }
}
