package com.greenfoxacademy.springwebapp.common.exceptions;

public class NotEnoughMaterialException extends RuntimeException {

  public static final String MESSAGE = "Not enough material";

  public NotEnoughMaterialException() {
    super(MESSAGE);
  }

}
