package com.greenfoxacademy.springwebapp.common.exceptions;

public class IdNotFoundException extends RuntimeException {

  public static final String message = "Id not found";

  public IdNotFoundException() {
    super(message);
  }

}
