package com.greenfoxacademy.springwebapp.customer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.springwebapp.admin.models.Admin;
import com.greenfoxacademy.springwebapp.common.models.ErrorDTO;
import com.greenfoxacademy.springwebapp.common.models.StatusResponseDTO;
import com.greenfoxacademy.springwebapp.customer.models.CustomerRequestDTO;
import com.greenfoxacademy.springwebapp.customer.models.CustomerResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class CustomerControllerIT {

  @Autowired
  MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private Authentication auth;
  private Admin testAdmin;

  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper();
    testAdmin = new Admin();
    auth = new UsernamePasswordAuthenticationToken(testAdmin, null, null);
  }

  @Test
  public void register_shouldReturn201() throws Exception {
    CustomerRequestDTO expectedRequestDTO = new CustomerRequestDTO();
    expectedRequestDTO.setName("defaultCompany1001");
    expectedRequestDTO.setEmail("johndoe@example.com");

    MvcResult mvcResult = mockMvc.perform(post("/api/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expectedRequestDTO))
                    .principal(auth))
            .andExpect(status().isCreated())
            .andReturn();

    CustomerResponseDTO actualResponseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            CustomerResponseDTO.class);

    assertEquals(expectedRequestDTO.getName(), actualResponseDTO.getName());
  }

  @Test
  public void register_shouldReturn400_whenEmailIsInvalid() throws Exception {
    CustomerRequestDTO expectedRequestDTO = new CustomerRequestDTO();
    expectedRequestDTO.setName("defaultCompany1002");
    expectedRequestDTO.setEmail("johndoeexample.com");

    MvcResult mvcResult = mockMvc.perform(post("/api/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expectedRequestDTO))
                    .principal(auth))
            .andExpect(status().isBadRequest())
            .andReturn();

    StatusResponseDTO statusResponseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            StatusResponseDTO.class);
    assertEquals("error", statusResponseDTO.getStatus());
    assertEquals("Email format is invalid.", statusResponseDTO.getMessage());
  }

  @Test
  public void register_shouldReturn409_whenNameIsAlreadyTaken() throws Exception {
    CustomerRequestDTO expectedRequestDTO = new CustomerRequestDTO();
    expectedRequestDTO.setName("defaultCompany1");

    MvcResult mvcResult = mockMvc.perform(post("/api/customer")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expectedRequestDTO))
                    .principal(auth))
            .andExpect(status().isConflict())
            .andReturn();

    StatusResponseDTO statusResponseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            StatusResponseDTO.class);
    assertEquals("error", statusResponseDTO.getStatus());
    assertEquals("Customer name is already taken.", statusResponseDTO.getMessage());
  }

  @Test
  public void getCustomerById_shouldReturn200() throws Exception {
    mockMvc.perform(get("/api/customer/1001")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(auth))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1001))
            .andExpect(jsonPath("$.name").value("defaultCompany1"));
  }

  @Test
  public void getCustomerById_shouldReturn404_whenIdNotFound() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/api/customer/1000")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(auth))
            .andExpect(status().isNotFound())
            .andReturn();

    ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            ErrorDTO.class);
    assertEquals("error", errorDTO.getStatus());
    assertEquals("Id not found", errorDTO.getMessage());
  }

}
