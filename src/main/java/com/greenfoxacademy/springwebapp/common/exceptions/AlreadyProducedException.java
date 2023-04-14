package com.greenfoxacademy.springwebapp.common.exceptions;

public class AlreadyProducedException extends RuntimeException {

  public static final String MESSAGE = "This product is already produced";

  public AlreadyProducedException() {
    super(MESSAGE);
  }

}
