package com.greenfoxacademy.springwebapp.customer.controllers;

import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyTakenNameException;
import com.greenfoxacademy.springwebapp.common.models.StatusResponseDTO;
import com.greenfoxacademy.springwebapp.customer.models.CustomerRequestDTO;
import com.greenfoxacademy.springwebapp.customer.models.CustomerResponseDTO;
import com.greenfoxacademy.springwebapp.customer.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

@Tag(name = "Customer Registration")
@RequestMapping("/api")
@AllArgsConstructor
@RestController
public class CustomerController {

  private CustomerService customerService;

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
  public ResponseEntity<?> register(@RequestBody CustomerRequestDTO customerRequestDTO) {
    try {
      CustomerResponseDTO createdCustomer = customerService.saveCustomer(customerRequestDTO);
      return ResponseEntity.status(CREATED).body(createdCustomer);
    } catch (AlreadyTakenNameException e) {
      return ResponseEntity.status(CONFLICT).body(new StatusResponseDTO("error", e.getMessage()));
    }
  }

  @Operation(summary = "Get Customer", description = "List customer by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = CustomerResponseDTO.class))),
  })
  @GetMapping("/customer/{id}")
  public ResponseEntity<?> getCustomerById(@PathVariable Integer id) {
    return ResponseEntity.ok(customerService.getCustomerById(id));
  }

}
