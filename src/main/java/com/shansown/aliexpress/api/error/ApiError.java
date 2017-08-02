package com.shansown.aliexpress.api.error;

public interface ApiError {

  long SUCCESS_CODE = 20010000;

  long getCode();

  String getMsg();
}
