package com.greenfoxacademy.springwebapp.product.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.greenfoxacademy.springwebapp.admin.models.Admin;
import com.greenfoxacademy.springwebapp.common.models.ErrorDTO;
import com.greenfoxacademy.springwebapp.material.repositories.MaterialRepository;
import com.greenfoxacademy.springwebapp.product.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductionControllerIT {

  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private Authentication auth;
  private Admin testAdmin;
  private MaterialRepository materialRepository;
  private ProductRepository productRepository;

  @Autowired
  public ProductionControllerIT(MaterialRepository materialRepository, ProductRepository productRepository) {
    this.materialRepository = materialRepository;
    this.productRepository = productRepository;
  }

  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    testAdmin = new Admin();
    auth = new UsernamePasswordAuthenticationToken(testAdmin, null, null);
  }


  @Test
  public void testProductionWithValidParamsShouldReturnMaterial() throws Exception {
    MvcResult mvcResult = mockMvc.perform(put("/api/production")
                    .param("productId", "1001")
                    .param("materialId", "1001"))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  public void testProductionWithInvalidProductIdShouldReturn404() throws Exception {
    MvcResult mvcResult = mockMvc.perform(put("/api/production")
                    .param("productId", "0")
                    .param("materialId", "1001"))
            .andExpect(status().isNotFound())
            .andReturn();

    String response = mvcResult.getResponse().getContentAsString();
    ErrorDTO errorDTO = objectMapper.readValue(response, ErrorDTO.class);
    assertEquals("Id not found", errorDTO.getMessage());
  }

  @Test
  public void testProductionWithInvalidMaterialIdShouldReturn404() throws Exception {
    MvcResult mvcResult = mockMvc.perform(put("/api/production")
                    .param("productId", "1003")
                    .param("materialId", "0"))
            .andExpect(status().isNotFound())
            .andReturn();

    String response = mvcResult.getResponse().getContentAsString();
    ErrorDTO errorDTO = objectMapper.readValue(response, ErrorDTO.class);
    assertEquals("Id not found", errorDTO.getMessage());
  }

  @Test
  public void testProductionWithQualityDifferenceShouldReturn406() throws Exception {
    MvcResult mvcResult = mockMvc.perform(put("/api/production")
                    .param("productId", "1003")
                    .param("materialId", "1001"))
            .andExpect(status().isNotAcceptable())
            .andReturn();

    String response = mvcResult.getResponse().getContentAsString();
    ErrorDTO errorDTO = objectMapper.readValue(response, ErrorDTO.class);
    assertEquals("Material and product quality difference", errorDTO.getMessage());
  }

}
