package com.greenfoxacademy.springwebapp.registration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.springwebapp.admin.repositories.AdminRepository;
import com.greenfoxacademy.springwebapp.admin.models.AdminRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerIT {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private AdminRepository adminRepository;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void register_should_returnHttp201_when_registrationIsSuccessful() throws Exception {
    String username = "newUser1";
    AdminRequestDTO reg = new AdminRequestDTO(username, "password");

    mockMvc.perform(post("/admin")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reg)))
            .andExpect(status().is(201))
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.username", is(username)))
            .andExpect(jsonPath("$.password").doesNotExist());
  }

  @Test
  public void register_should_returnHttp409_when_usernameIsAlreadyTaken() throws Exception {
    AdminRequestDTO reg = new AdminRequestDTO("defaultUser1", "password");

    mockMvc.perform(post("/admin")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reg)))
            .andExpect(status().is(409))
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Username is already taken.")));
  }

  @Test
  public void register_should_returnHttp406_when_passwordIsLess_than8Character() throws Exception {
    String username = "newUser2";
    String password = "pass";

    AdminRequestDTO reg = new AdminRequestDTO(username, password);

    mockMvc.perform(post("/admin")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reg)))
            .andExpect(status().is(406))
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Password must be at least 8 characters.")));
  }

  @Test
  public void register_should_returnHttp400_when_usernameIsMissing() throws Exception {
    AdminRequestDTO reg = new AdminRequestDTO(null, "password");

    mockMvc.perform(post("/admin")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reg)))
            .andExpect(status().is(400))
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Username is required.")));
  }

  @Test
  public void register_should_returnHttp400_when_passwordIsMissing() throws Exception {
    String username = "newUser3";

    AdminRequestDTO reg = new AdminRequestDTO(username, null);

    mockMvc.perform(post("/admin")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reg)))
            .andExpect(status().is(400))
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Password is required.")));
  }

  @Test
  public void register_should_returnHttp400_when_usernameAndPasswordAreMissing() throws Exception {
    AdminRequestDTO reg = new AdminRequestDTO(null, null);

    mockMvc.perform(post("/admin")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reg)))
            .andExpect(status().is(400))
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", anyOf(is("Username and password are required."),
                    is("Password and username are required."))));
  }

  @Test
  public void register_should_returnHttp400_when_emptyRequestWasSent() throws Exception {
    AdminRequestDTO reg = null;

    mockMvc.perform(post("/admin")
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reg)))
            .andExpect(status().is(400))
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Username and password are required.")));
  }

}
