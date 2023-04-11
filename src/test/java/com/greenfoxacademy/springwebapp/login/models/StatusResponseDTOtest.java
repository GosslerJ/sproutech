package com.greenfoxacademy.springwebapp.login.models;

import com.greenfoxacademy.springwebapp.common.models.StatusResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusResponseDTOtest {

  StatusResponseDTO statusResponseDTO = new StatusResponseDTO();

  @Test
  void setStatusOk() {
    statusResponseDTO.setStatusOk();
    assertEquals("ok", statusResponseDTO.getStatus());
  }

  @Test
  void setStatusError() {
    statusResponseDTO.setStatusError();
    assertEquals("error", statusResponseDTO.getStatus());
  }

}