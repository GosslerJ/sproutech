package com.greenfoxacademy.springwebapp.material.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.greenfoxacademy.springwebapp.admin.models.Admin;
import com.greenfoxacademy.springwebapp.common.models.ErrorDTO;
import com.greenfoxacademy.springwebapp.material.models.Material;
import com.greenfoxacademy.springwebapp.material.models.MaterialRequestDTO;
import com.greenfoxacademy.springwebapp.material.models.MaterialResponseDTO;
import com.greenfoxacademy.springwebapp.material.repositories.MaterialRepository;
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

import java.util.List;

import static com.greenfoxacademy.springwebapp.TestFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class MaterialControllerIT {

  @Autowired
  MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private Authentication auth;
  private Admin testAdmin;
  private MaterialRepository materialRepository;

  @Autowired
  public MaterialControllerIT(MaterialRepository materialRepository) {
    this.materialRepository = materialRepository;
  }


  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    testAdmin = new Admin();
    auth = new UsernamePasswordAuthenticationToken(testAdmin, null, null);
  }

  @Test
  public void register_shouldReturn201() throws Exception {
    MaterialRequestDTO expectedRequestDTO = materialRequestDtoBuilder();

    MvcResult mvcResult = mockMvc.perform(post("/api/material")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(expectedRequestDTO))
                    .principal(auth))
            .andExpect(status().isCreated())
            .andReturn();

    MaterialResponseDTO actualResponseDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            MaterialResponseDTO.class);
    actualResponseDTO.setId(1);

    assertEquals(expectedRequestDTO.getQuality(), actualResponseDTO.getQuality());
  }

  @Test
  public void filterMaterials_shouldReturnCorrectMaterials_whenTwoParams() throws Exception {
    List<Material> expectedMaterials = materialListBuilder();

    MvcResult mvcResult = mockMvc.perform(get("/api/material")
                    .param("quality", "25CrMo4")
                    .param("size", "10.68")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(auth))
            .andExpect(status().isOk())
            .andReturn();

    List<Material> actualMaterials = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
        new com.fasterxml.jackson.core.type.TypeReference<List<Material>>() {
      });

    assertEquals(expectedMaterials.size(), actualMaterials.size());
  }

  @Test
  public void filterMaterials_shouldReturnCorrectMaterials_whenSizeParam() throws Exception {
    List<Material> expectedMaterials = materialListBuilder();

    MvcResult mvcResult = mockMvc.perform(get("/api/material")
                    .param("size", "10.68")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(auth))
            .andExpect(status().isOk())
            .andReturn();

    List<Material> actualMaterials = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
        new com.fasterxml.jackson.core.type.TypeReference<List<Material>>() {
      });

    assertEquals(expectedMaterials.size(), actualMaterials.size());
  }

  @Test
  public void filterMaterials_shouldReturnCorrectMaterials_whenQualityParam() throws Exception {
    List<Material> expectedMaterials = materialListBuilder();

    MvcResult mvcResult = mockMvc.perform(get("/api/material")
                    .param("quality", "25CrMo4")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(auth))
            .andExpect(status().isOk())
            .andReturn();

    List<Material> actualMaterials = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
        new com.fasterxml.jackson.core.type.TypeReference<List<Material>>() {
      });

    assertEquals(expectedMaterials.size(), actualMaterials.size());
  }

  @Test
  public void transferMaterial_shouldReturn200() throws Exception {
    Material material = transferMaterialBuilder();
    materialRepository.save(material);

    MvcResult result = mockMvc.perform(put("/api/material")
                    .param("quality", "quality")
                    .param("size", "20.0")
                    .param("quantity", "5")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(auth))
            .andExpect(status().isOk())
            .andReturn();

    Material transferredMaterial = objectMapper.readValue(result.getResponse().getContentAsString(), Material.class);
    assertEquals(1, material.getWarehouse().getId());
    assertEquals(2, transferredMaterial.getWarehouse().getId());
    assertEquals("quality", transferredMaterial.getQuality());
    assertEquals(20.0, transferredMaterial.getSize());
    assertEquals(5, transferredMaterial.getQuantity());
  }

  @Test
  public void transferMaterial_shouldReturn406_whenNotEnoughMaterial() throws Exception {
    Material material = transferMaterialBuilder();
    materialRepository.save(material);
    MvcResult mvcResult = mockMvc.perform(put("/api/material")
                    .param("quality", "quality")
                    .param("size", "20.0")
                    .param("quantity", "999999999")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(auth))
            .andExpect(status().isNotAcceptable())
            .andReturn();

    ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            ErrorDTO.class);
    assertEquals("error", errorDTO.getStatus());
    assertEquals("Not enough material", errorDTO.getMessage());
  }

}
