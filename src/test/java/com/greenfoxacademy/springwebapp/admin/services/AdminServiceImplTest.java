package com.greenfoxacademy.springwebapp.admin.services;

import com.greenfoxacademy.springwebapp.admin.models.Admin;
import com.greenfoxacademy.springwebapp.admin.repositories.AdminRepository;
import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyTakenNameException;
import com.greenfoxacademy.springwebapp.common.exceptions.InvalidPasswordException;
import com.greenfoxacademy.springwebapp.admin.models.AdminRequestDTO;
import com.greenfoxacademy.springwebapp.admin.models.AdminResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

  @Mock
  private AdminRepository mockAdminRepository;
  @InjectMocks
  private AdminServiceImpl userService;
  String username;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    userService = new AdminServiceImpl(mockAdminRepository);
    username = "admin";
  }

  @Test
  public void validateRegistration_doesNotThrowException_when_usernameIsNotTakenAndPwIsValid() {
    AdminRequestDTO reg = new AdminRequestDTO(username, "password123");
    when(mockAdminRepository.findByUsername(username)).thenReturn(Optional.empty());

    userService.validateRegistration(reg);
  }

  @Test
  public void validateRegistration_throwsException_when_usernameIsAlreadyTaken() {
    String username = "defaultUser";
    AdminRequestDTO reg = new AdminRequestDTO(username, "password");
    when(mockAdminRepository.findByUsername(username)).thenReturn(Optional.of(new Admin()));

    try {
      userService.validateRegistration(reg);
    } catch (Exception e) {
      assertEquals(AlreadyTakenNameException.class, e.getClass());
      assertEquals("Username is already taken.", e.getMessage());
    }
  }

  @Test
  public void validateRegistration_throwsException_when_passwordIsInvalid() {
    AdminRequestDTO reg = new AdminRequestDTO(username, "pass");
    when(mockAdminRepository.findByUsername(username)).thenReturn(Optional.empty());

    try {
      userService.validateRegistration(reg);
    } catch (Exception e) {
      assertEquals(InvalidPasswordException.class, e.getClass());
      assertEquals("Password must be at least 8 characters.", e.getMessage());
    }
  }

  @Test
  public void convert_returns_withRegistrationResponseDTO_when_userIsGiven() {
    Admin admin = new Admin(username, "password");
    admin.setId(1);

    AdminResponseDTO expected = new AdminResponseDTO(admin.getId(),
        admin.getUsername());

    AdminResponseDTO actual = userService.convert(admin);

    assertEquals(expected, actual);
  }

  @Test
  public void convert_returns_null_when_userIsNotGiven() {
    Admin admin = null;
    AdminResponseDTO expected = null;

    AdminResponseDTO actual = userService.convert(admin);

    assertEquals(expected, actual);
  }

}