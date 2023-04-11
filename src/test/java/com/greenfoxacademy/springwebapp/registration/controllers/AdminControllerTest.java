package com.greenfoxacademy.springwebapp.registration.controllers;

import com.greenfoxacademy.springwebapp.admin.controllers.AdminController;
import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyTakenNameException;
import com.greenfoxacademy.springwebapp.common.models.StatusResponseDTO;
import com.greenfoxacademy.springwebapp.admin.services.AdminService;
import com.greenfoxacademy.springwebapp.admin.services.AdminServiceImpl;
import com.greenfoxacademy.springwebapp.common.exceptions.InvalidPasswordException;
import com.greenfoxacademy.springwebapp.admin.models.AdminRequestDTO;
import com.greenfoxacademy.springwebapp.admin.models.AdminResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AdminControllerTest {

  private AdminController adminController;

  private AdminService mockAdminService;

  @BeforeEach
  void setUp() {
    mockAdminService = Mockito.mock(AdminServiceImpl.class);
    adminController = new AdminController(mockAdminService);
  }

  @Test
  public void register_should_returnHttp201_when_registrationIsSuccessful() {
    AdminRequestDTO reg = new AdminRequestDTO("newUser", "password");
    AdminResponseDTO response = new AdminResponseDTO(1, "newUser222");
    when(mockAdminService.saveAdmin(reg)).thenReturn(response);

    ResponseEntity<?> actual = adminController.register(reg);

    assertEquals(HttpStatus.CREATED, actual.getStatusCode());
    assertEquals(response, actual.getBody());
  }

  @Test
  public void register_should_returnHttp409_when_usernameIsAlreadyTaken() {
    AdminRequestDTO reg = new AdminRequestDTO("newUser", "password");
    String errorMessage = "error message";
    when(mockAdminService.saveAdmin(reg)).thenThrow(new AlreadyTakenNameException(errorMessage));
    StatusResponseDTO expectedBody = new StatusResponseDTO("error", errorMessage);

    ResponseEntity<?> actual = adminController.register(reg);

    assertEquals(409, actual.getStatusCode().value());
    assertEquals(expectedBody, actual.getBody());
  }

  @Test
  public void register_should_returnHttp406_when_passwordIsShorterThan8Character() {
    AdminRequestDTO reg = new AdminRequestDTO("newUser", "pass");
    String errorMessage = "error message";
    when(mockAdminService.saveAdmin(reg)).thenThrow(new InvalidPasswordException(errorMessage));
    StatusResponseDTO expectedBody = new StatusResponseDTO("error", errorMessage);

    ResponseEntity<?> actual = adminController.register(reg);

    assertEquals(406, actual.getStatusCode().value());
    assertEquals(expectedBody, actual.getBody());
  }

}