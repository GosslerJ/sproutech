package com.greenfoxacademy.springwebapp.admin.controllers;

import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyTakenNameException;
import com.greenfoxacademy.springwebapp.common.models.StatusResponseDTO;
import com.greenfoxacademy.springwebapp.common.exceptions.InvalidPasswordException;
import com.greenfoxacademy.springwebapp.admin.models.AdminRequestDTO;
import com.greenfoxacademy.springwebapp.admin.models.AdminResponseDTO;
import com.greenfoxacademy.springwebapp.admin.services.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@Tag(name = "Admin Registration")
@RestController
public class AdminController {

  private AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @Operation(summary = "Admin Registration", description = "Register a new admin")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "successful registration",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = AdminResponseDTO.class))),
          @ApiResponse(responseCode = "409", description = "username is already taken",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = StatusResponseDTO.class))),
          @ApiResponse(responseCode = "406", description = "invalid password",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = StatusResponseDTO.class))),
  })
  @PostMapping("/admin")
  public ResponseEntity<?> register(@Valid @RequestBody AdminRequestDTO adminRequestDTO) {
    try {
      AdminResponseDTO createdAdmin = adminService.saveAdmin(adminRequestDTO);
      return ResponseEntity.status(CREATED).body(createdAdmin);
    } catch (AlreadyTakenNameException e) {
      return ResponseEntity.status(CONFLICT).body(new StatusResponseDTO("error", e.getMessage()));
    } catch (InvalidPasswordException e) {
      return ResponseEntity.status(NOT_ACCEPTABLE).body(new StatusResponseDTO("error", e.getMessage()));
    }
  }

}
