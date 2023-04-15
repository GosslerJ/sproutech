package com.greenfoxacademy.springwebapp.customer.services;

import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyTakenNameException;
import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.common.exceptions.InvalidEmailException;
import com.greenfoxacademy.springwebapp.customer.models.Customer;
import com.greenfoxacademy.springwebapp.customer.models.CustomerRequestDTO;
import com.greenfoxacademy.springwebapp.customer.models.CustomerResponseDTO;
import com.greenfoxacademy.springwebapp.customer.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

  public static final String REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+[a-zA-Z]{2,}$";

  private CustomerRepository customerRepository;

  @Override
  public CustomerResponseDTO saveCustomer(CustomerRequestDTO reg) {
    validateRegistration(reg);
    Customer customer = Customer.builder()
            .name(reg.getName())
            .contactPerson(reg.getContactPerson())
            .email(reg.getEmail())
            .phoneNumber(reg.getPhoneNumber())
            .taxNumber(reg.getTaxNumber())
            .zipCode(reg.getZipCode())
            .city(reg.getCity())
            .address(reg.getAddress())
            .country(reg.getCountry())
            .build();
    customerRepository.save(customer);
    return convert(customer); //new CustomerResponseDTO(customer.getId(), customer.getName());
  }

  public CustomerResponseDTO convert(Customer customer) {
    return CustomerResponseDTO.builder()
            .id(customer.getId())
            .name(customer.getName())
            .build();
  }

  @Override
  public void validateRegistration(CustomerRequestDTO reg) throws AlreadyTakenNameException, InvalidEmailException {
    if (customerRepository.findByName(reg.getName()).isPresent())
      throw new AlreadyTakenNameException("Customer name is already taken.");
    if (reg.getEmail() == null || reg.getEmail().trim().length() < 3 || !validateEmail(reg.getEmail()))
      throw new InvalidEmailException("Email format is invalid.");
  }

  public static boolean validateEmail(String email) {
    return Pattern.compile(REGEX)
            .matcher(email)
            .matches();
  }

  @Override
  public Customer getCustomerById(Integer id) throws IdNotFoundException {
    return customerRepository.findById(id).orElseThrow(IdNotFoundException::new);
  }

}
