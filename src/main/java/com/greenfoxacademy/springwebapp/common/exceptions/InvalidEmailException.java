package com.greenfoxacademy.springwebapp.common.exceptions;

public class InvalidEmailException extends RuntimeException {

  public InvalidEmailException(String errorMessage) {
    super(errorMessage);
  }
}
