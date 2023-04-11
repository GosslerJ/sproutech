package com.greenfoxacademy.springwebapp.customer.controllers;

import com.greenfoxacademy.springwebapp.customer.models.CustomerRequestDTO;
import com.greenfoxacademy.springwebapp.customer.models.CustomerResponseDTO;
import com.greenfoxacademy.springwebapp.customer.services.CustomerService;
import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyTakenNameException;
import com.greenfoxacademy.springwebapp.common.models.StatusResponseDTO;
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

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "Customer Registration")
@RestController
public class CustomerController {

  private CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Operation(summary = "Customer Registration", description = "Register a new customer")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "successful registration",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CustomerResponseDTO.class))),
          @ApiResponse(responseCode = "409", description = "name is already taken",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = StatusResponseDTO.class))),
  })
  @PostMapping("/customer")
  public ResponseEntity<?> register(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
    try {
      CustomerResponseDTO createdCustomer = customerService.saveCustomer(customerRequestDTO);
      return ResponseEntity.status(CREATED).body(createdCustomer);
    } catch (AlreadyTakenNameException e) {
      return ResponseEntity.status(CONFLICT).body(new StatusResponseDTO("error", e.getMessage()));
    }
  }

}