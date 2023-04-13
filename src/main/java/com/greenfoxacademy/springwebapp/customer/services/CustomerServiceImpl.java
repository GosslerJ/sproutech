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

  public static final String REGEX =
          "^[\\\\w!#$%&'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$\n";

  private CustomerRepository customerRepository;

  @Override
  public CustomerResponseDTO saveCustomer(CustomerRequestDTO reg)
          throws AlreadyTakenNameException, InvalidEmailException {
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
    return new CustomerResponseDTO(customer.getId(), customer.getName());
  }

  @Override
  public void validateRegistration(CustomerRequestDTO reg) throws AlreadyTakenNameException, InvalidEmailException {
    if (customerRepository.findByName(reg.getName()).isPresent())
      throw new AlreadyTakenNameException("Customer name is already taken.");
    if (reg.getEmail() == null || reg.getEmail().trim().length() < 3 || validateEmail(reg.getEmail(), REGEX))
      throw new InvalidEmailException("Email format is not valid.");
  }

  public static boolean validateEmail(String email, String regex) {
    return Pattern.compile(regex)
            .matcher(email)
            .matches();
  }

  @Override
  public Customer getCustomerById(Integer id) throws IdNotFoundException {
    return customerRepository.findById(id).orElseThrow(IdNotFoundException::new);
  }

}
