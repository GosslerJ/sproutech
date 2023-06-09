package com.greenfoxacademy.springwebapp.login.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
  @NotBlank(message = "Username is required.")
  private String username;
  @NotBlank(message = "Password is required.")
  private String password;

}
