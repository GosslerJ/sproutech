package com.greenfoxacademy.springwebapp.login.controller;

import com.greenfoxacademy.springwebapp.common.models.StatusResponseDTO;
import com.greenfoxacademy.springwebapp.login.models.LoginDTO;
import com.greenfoxacademy.springwebapp.login.models.TokenResponseDTO;
import com.greenfoxacademy.springwebapp.login.service.LoginServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Login Controller", description = "Login related endpoints")
@AllArgsConstructor
@RestController
public class LoginController {

  private LoginServiceImpl loginService;

  @Operation(summary = "Admin Login", description = "Login to private endpoints")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful registration",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = TokenResponseDTO.class))),
          @ApiResponse(responseCode = "401", description = "username or password is incorrect",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = StatusResponseDTO.class))),
  })
  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody LoginDTO login) {
    return ResponseEntity.status(200).body(loginService.login(login));
  }

}
