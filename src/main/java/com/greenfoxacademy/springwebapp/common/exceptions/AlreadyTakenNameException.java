package com.greenfoxacademy.springwebapp.common.exceptions;

public class AlreadyTakenNameException extends RuntimeException {

  public AlreadyTakenNameException(String errorMessage) {
    super(errorMessage);
  }

}