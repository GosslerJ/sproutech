package com.greenfoxacademy.springwebapp.admin.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDTO {

  @NotBlank(message = "Username is required.")
  private String username;
  @NotBlank(message = "Password is required.")
  private String password;

}
