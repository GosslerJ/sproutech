package com.greenfoxacademy.springwebapp.common.exceptions;

public class InvalidPasswordException extends RuntimeException {

  public InvalidPasswordException(String errorMessage) {
    super(errorMessage);
  }
}