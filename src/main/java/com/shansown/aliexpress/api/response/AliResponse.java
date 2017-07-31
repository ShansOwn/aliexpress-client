package com.shansown.aliexpress.api.response;

import lombok.Data;

@Data
public class AliResponse<T extends AliResult> {
  private final T result;
  private final long errorCode;
}
