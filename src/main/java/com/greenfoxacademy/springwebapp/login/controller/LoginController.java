package com.greenfoxacademy.springwebapp.login.controller;

import com.greenfoxacademy.springwebapp.login.models.LoginDTO;
import com.greenfoxacademy.springwebapp.login.service.LoginServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class LoginController {

  private LoginServiceImpl loginService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginDTO login) {
    return ResponseEntity.status(200).body(loginService.login(login));
  }

}
