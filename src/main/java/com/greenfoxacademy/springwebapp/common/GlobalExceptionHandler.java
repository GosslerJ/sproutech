package com.greenfoxacademy.springwebapp.common;

import com.greenfoxacademy.springwebapp.common.exceptions.*;
import com.greenfoxacademy.springwebapp.common.models.ErrorDTO;
import com.greenfoxacademy.springwebapp.common.models.StatusResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {
    StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
    statusResponseDTO.setStatusError();
    List<String> errorMessages = collectErrorMessages(ex);
    String errorMessage = concatErrorMessages(errorMessages);
    statusResponseDTO.setMessage(errorMessage);
    return new ResponseEntity<>(statusResponseDTO, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
          HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    StatusResponseDTO responseDTO = new StatusResponseDTO();
    responseDTO.setStatusError();
    responseDTO.setMessage("Username and password are required.");
    return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidPasswordException.class)
  public ResponseEntity<ErrorDTO> handle(InvalidPasswordException ex) {
    ErrorDTO response = new ErrorDTO(ex.getMessage());
    return ResponseEntity.status(400).body(response);
  }

//  @ExceptionHandler(InvalidEmailException.class)
//  public ResponseEntity<ErrorDTO> handle(InvalidEmailException ex) {
//    ErrorDTO response = new ErrorDTO(ex.getMessage());
//    return ResponseEntity.status(400).body(response);
//  }

  @ExceptionHandler(LoginFailureException.class)
  public ResponseEntity<StatusResponseDTO> handle(LoginFailureException ex) {
    StatusResponseDTO statusResponseDTO = new StatusResponseDTO();
    statusResponseDTO.setStatusError();
    statusResponseDTO.setMessage(ex.getMessage());
    return ResponseEntity.status(401).body(statusResponseDTO);
  }

  @ExceptionHandler(IdNotFoundException.class)
  public ResponseEntity<ErrorDTO> handle(IdNotFoundException ex) {
    ErrorDTO response = new ErrorDTO(ex.getMessage());
    return ResponseEntity.status(404).body(response);
  }

//  @ExceptionHandler({NotEnoughMaterialException.class, AlreadyProducedException.class,
//          QualityDifferenceException.class})
//  public ResponseEntity<ErrorDTO> handle(RuntimeException ex) {
//    ErrorDTO response = new ErrorDTO(ex.getMessage());
//    return ResponseEntity.status(406).body(response);
//  }

  public List<String> collectErrorMessages(MethodArgumentNotValidException ex) {
    return ex.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());
  }

  public String concatErrorMessages(List<String> errorMessages) {
    if (errorMessages.size() > 1) {

      List<String> wrongFields = collectWrongField(errorMessages);
      String errorMessage = wrongFields.get(0);

      if (wrongFields.size() < 3) {
        errorMessage = errorMessageForTwoWrongFields(wrongFields, errorMessage);
        return errorMessage;
      } else {
        errorMessage = errorMessageForMoreThanTwoWrongFields(wrongFields, errorMessage);
        return errorMessage;
      }
    }
    return errorMessages.get(0);
  }

  private String errorMessageForMoreThanTwoWrongFields(List<String> wrongFields, String errorMessage) {
    for (int i = 1; i < wrongFields.size(); i++) {
      wrongFields.set(i, wrongFields.get(i).toLowerCase());

      if (i < wrongFields.size() - 1) {
        errorMessage = String.join(", ", errorMessage, wrongFields.get(i));
      } else {
        errorMessage = String.join(" and ", errorMessage, wrongFields.get(i));
      }
    }
    errorMessage = String.join(" ", errorMessage, "are required.");
    return errorMessage;
  }

  private String errorMessageForTwoWrongFields(List<String> wrongFields, String errorMessage) {
    for (int i = 1; i < wrongFields.size(); i++) {
      wrongFields.set(i, wrongFields.get(i).toLowerCase());
      errorMessage = String.join(" and ", errorMessage, wrongFields.get(i));
      errorMessage = String.join(" ", errorMessage, "are required.");
    }
    return errorMessage;
  }

  private List<String> collectWrongField(List<String> errorMessages) {
    return errorMessages.stream()
            .map(s -> s.split(" "))
            .map(s -> s[0])
            .collect(Collectors.toList());
  }

}