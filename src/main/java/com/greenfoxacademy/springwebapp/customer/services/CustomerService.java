package com.greenfoxacademy.springwebapp.customer.services;

import com.greenfoxacademy.springwebapp.customer.models.Customer;
import com.greenfoxacademy.springwebapp.customer.models.CustomerRequestDTO;
import com.greenfoxacademy.springwebapp.customer.models.CustomerResponseDTO;

public interface CustomerService {
  CustomerResponseDTO saveCustomer(CustomerRequestDTO customerRequestDTO);

  Customer getCustomerById(Integer id);
}
