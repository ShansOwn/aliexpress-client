package com.shansown.aliexpress.api.error;

public interface AliApiError {

  long SUCCESS_CODE = 20010000;

  long getCode();

  String getMsg();
}
