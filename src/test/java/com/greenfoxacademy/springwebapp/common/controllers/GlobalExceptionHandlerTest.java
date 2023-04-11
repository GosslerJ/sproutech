package com.greenfoxacademy.springwebapp.common.controllers;

import com.greenfoxacademy.springwebapp.common.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {
  GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

  @Test
  void handleMethodArgumentNotValid() {
  }

  @Test
  void collectErrorMessages() {

  }

  @Test
  void handle() {
  }

  @Test
  void concatErrorMessages() {
    List<String> errorMessages1 = new ArrayList<>(Arrays.asList("Username is required."));
    List<String> errorMessages2 = new ArrayList<>(Arrays.asList("Username is required.", "Password is required."));
    List<String> errorMessages3 = new ArrayList<>(Arrays.asList(
          "Username is required.",
          "Password is required.",
          "Token is required"
    ));
    assertEquals("Username is required.", globalExceptionHandler.concatErrorMessages(errorMessages1));
    assertEquals("Username and password are required.",
            globalExceptionHandler.concatErrorMessages(errorMessages2));
    assertEquals("Username, password and token are required.",
            globalExceptionHandler.concatErrorMessages(errorMessages3));
  }

}