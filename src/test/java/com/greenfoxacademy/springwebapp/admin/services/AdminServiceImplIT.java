package com.greenfoxacademy.springwebapp.admin.services;

import com.greenfoxacademy.springwebapp.admin.models.Admin;
import com.greenfoxacademy.springwebapp.admin.repositories.AdminRepository;
import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyTakenNameException;
import com.greenfoxacademy.springwebapp.common.exceptions.InvalidPasswordException;
import com.greenfoxacademy.springwebapp.admin.models.AdminRequestDTO;
import com.greenfoxacademy.springwebapp.admin.models.AdminResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class AdminServiceImplIT {

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private AdminService adminService;

  @BeforeEach
  void setUp() {
    adminService = new AdminServiceImpl(adminRepository);
  }

  @Test
  public void saveAdmin_should_returnRegistrationResponseDTO_when_givenCorrectAdmin() throws Exception {
    String username = "newUser1";
    AdminRequestDTO reg = new AdminRequestDTO(username, "password123");

    Admin admin = new Admin(reg.getUsername(), reg.getPassword());
    admin.setId(2);

    AdminResponseDTO expected = new AdminResponseDTO(admin.getId(),
        admin.getUsername());
    AdminResponseDTO actual = adminService.saveAdmin(reg);

    assertEquals(expected.getUsername(), actual.getUsername());
    assertTrue(actual.getId() > 0);
  }

  @Test
  public void saveAdmin_should_throwsException_when_usernameIsAlreadyTaken() {
    String username = "username4";
    AdminRequestDTO reg = new AdminRequestDTO(username, "password123");

    try {
      adminService.saveAdmin(reg);
    } catch (Exception e) {
      assertEquals(AlreadyTakenNameException.class, e.getClass());
      assertEquals("Username is already taken.", e.getMessage());
    }
  }

  @Test
  public void saveUser_throwsException_when_passwordIsInvalid() {
    String username = "username5";
    AdminRequestDTO reg = new AdminRequestDTO(username, "pass");

    try {
      adminService.validateRegistration(reg);
    } catch (Exception e) {
      assertEquals(InvalidPasswordException.class, e.getClass());
      assertEquals("Password must be at least 8 characters.", e.getMessage());
    }
  }

}