package com.greenfoxacademy.springwebapp.common.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusResponseDTO {
  private String status;
  private String message;

  public void setStatusOk() {
    this.setStatus("ok");
  }

  public void setStatusError() {
    this.setStatus("error");
  }

}
