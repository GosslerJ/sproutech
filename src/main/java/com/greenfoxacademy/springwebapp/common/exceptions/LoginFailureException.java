package com.greenfoxacademy.springwebapp.common.exceptions;

public class LoginFailureException extends RuntimeException {

  public LoginFailureException() {
    super("Username or password is incorrect.");
  }

}