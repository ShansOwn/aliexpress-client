package com.shansown.aliexpress.api.error;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AliRequestError {
  private final int code;
  private final String msg;
}
