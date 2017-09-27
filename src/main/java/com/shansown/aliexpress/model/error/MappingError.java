package com.shansown.aliexpress.model.error;

public class MappingError extends AppError {

  public MappingError(String message) {
    super(message);
  }

  public MappingError(String message, Throwable cause) {
    super(message, cause);
  }
}
