package com.greenfoxacademy.springwebapp.common.exceptions;

public class AlreadyProducedException extends RuntimeException {

  public static final String MESSAGE = "Already produced";

  public AlreadyProducedException() {
    super(MESSAGE);
  }

}
