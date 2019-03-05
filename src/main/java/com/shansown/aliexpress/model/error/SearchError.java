package com.shansown.aliexpress.model.error;

public class SearchError extends AppError {

  public SearchError(String message) {
    super(message);
  }

  public SearchError(String message, Throwable cause) {
    super(message, cause);
  }
}
