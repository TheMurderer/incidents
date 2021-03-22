package com.aircall.test.Incidents.exception;

public class AcknowledgedTimerNotFound extends RuntimeException{


  public AcknowledgedTimerNotFound(String message, Throwable cause) {
    super(message, cause);
  }

  public AcknowledgedTimerNotFound(String message) {
    super(message);
  }
}
