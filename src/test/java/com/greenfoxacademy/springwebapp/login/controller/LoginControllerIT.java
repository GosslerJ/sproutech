package com.greenfoxacademy.springwebapp.login.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.springwebapp.login.models.LoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerIT {

  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    objectMapper = new ObjectMapper();

  }

  @Test
  public void login_should_returnHttp200_when_login_is_successful() throws Exception {
    LoginDTO loginDTO = new LoginDTO("defaultUser1", "password");
    mockMvc.perform(post("/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(loginDTO)))
          .andExpect(status().isOk())
          .andExpect(content().contentType(APPLICATION_JSON))
          .andExpect(jsonPath("$.status", is("ok")))
          .andExpect(jsonPath("$.token").isString())
          .andExpect(jsonPath("$.token").isNotEmpty());
  }

  @Test
  void login_should_returnHttp400_when_only_password_sent() throws Exception  {
    LoginDTO loginDTO = new LoginDTO("", "password");
    mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
          .andExpect(status().isBadRequest())
          .andExpect(content().contentType(APPLICATION_JSON))
          .andExpect(jsonPath("$.status", is("error")))
          .andExpect(jsonPath("$.message", is("Username is required.")));
  }

  @Test
  void login_should_returnHttp400_when_only_username_sent() throws Exception  {
    LoginDTO loginDTO = new LoginDTO("defaultUser1", "");
    mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
          .andExpect(status().is(400))
          .andExpect(content().contentType(APPLICATION_JSON))
          .andExpect(jsonPath("$.status", is("error")))
          .andExpect(jsonPath("$.message", is("Password is required.")));
  }

  @Test
  void login_should_returnHttp400_when_username_and_password_isEmpty() throws Exception  {
    LoginDTO loginDTO = new LoginDTO("", "");
    mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
          .andExpect(status().isBadRequest())
          .andExpect(content().contentType(APPLICATION_JSON))
          .andExpect(jsonPath("$.status", is("error")))
          .andExpect(jsonPath("$.message", anyOf(
                  is("Username and password are required."),
                  is("Password and username are required."))));
  }

  @Test
  void login_should_returnHttp401_when_password_is_incorrect() throws Exception  {
    LoginDTO loginDTO = new LoginDTO("defaultUser1", "password2");
    mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
          .andExpect(status().is(401))
          .andExpect(content().contentType(APPLICATION_JSON))
          .andExpect(jsonPath("$.status", is("error")))
          .andExpect(jsonPath("$.message", is("Username or password is incorrect.")));
  }

  @Test
  void login_should_returnHttp401_when_username_is_incorrect() throws Exception  {
    LoginDTO loginDTO = new LoginDTO("defaultUser0", "password");
    mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(loginDTO)))
            .andExpect(status().is(401))
            .andExpect(content().contentType(APPLICATION_JSON))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Username or password is incorrect.")));
  }

}