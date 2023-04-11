package com.greenfoxacademy.springwebapp.customer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDTO {

  @NotBlank(message = "Name is required.") // TODO: add validation
  private String name;
  @NotBlank(message = "Contact is required.")
  private String contactPerson;
  @NotBlank(message = "Email is required.")
  private String email;
  private String phoneNumber;
  private String taxNumber;
  @NotBlank(message = "Zip is required.")
  private String zipCode;
  @NotBlank(message = "City is required.")
  private String city;
  @NotBlank(message = "Address is required.")
  private String address;
  private String country;

}
