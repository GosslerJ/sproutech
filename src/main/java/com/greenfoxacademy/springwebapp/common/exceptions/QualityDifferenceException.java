package com.greenfoxacademy.springwebapp.common.exceptions;

public class QualityDifferenceException extends RuntimeException {
  public static final String MESSAGE = "Material and product quality difference";

  public QualityDifferenceException() {
    super(MESSAGE);
  }

}
