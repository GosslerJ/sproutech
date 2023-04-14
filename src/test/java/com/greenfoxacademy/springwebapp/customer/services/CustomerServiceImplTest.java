package com.greenfoxacademy.springwebapp.customer.services;

import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyTakenNameException;
import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.common.exceptions.InvalidEmailException;

import com.greenfoxacademy.springwebapp.customer.models.Customer;
import com.greenfoxacademy.springwebapp.customer.models.CustomerRequestDTO;
import com.greenfoxacademy.springwebapp.customer.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

  private CustomerService customerService;
  @Mock
  private CustomerRepository customerRepository;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    customerService = new CustomerServiceImpl(customerRepository);
  }

  @Test
  public void saveCustomer_DuplicateName_ThrowsAlreadyTakenNameException() {
    CustomerRequestDTO requestDTO1 = new CustomerRequestDTO();
    requestDTO1.setName("John Doe");
    requestDTO1.setEmail("johndoe1@example.com");

    CustomerRequestDTO requestDTO2 = new CustomerRequestDTO();
    requestDTO2.setName("John Doe");
    requestDTO2.setEmail("johndoe2@example.com");

    doThrow(AlreadyTakenNameException.class).when(customerRepository).save(any(Customer.class));

    assertThrows(AlreadyTakenNameException.class, () -> {
      customerService.saveCustomer(requestDTO1);
      customerService.saveCustomer(requestDTO2);
    });
  }

  @Test
  void validateRegistration_ValidRequest_DoesNotThrowException() {
    // arrange
    CustomerRequestDTO requestDTO = new CustomerRequestDTO();
    requestDTO.setName("John Doe");
    requestDTO.setEmail("johndoe@example.com");

    // mock repository to return empty Optional to simulate no existing customer with same name
    doReturn(Optional.empty()).when(customerRepository).findByName(requestDTO.getName());

    // act/assert
    assertDoesNotThrow(() -> {
      customerService.validateRegistration(requestDTO);
    });
  }

  @Test
  void validateRegistration_AlreadyTakenName_ThrowsAlreadyTakenNameException() {
    // arrange
    CustomerRequestDTO requestDTO = new CustomerRequestDTO();
    requestDTO.setName("John Doe");
    requestDTO.setEmail("johndoe@example.com");

    // mock repository to return non-empty Optional to simulate existing customer with same name
    Customer existingCustomer = Customer.builder().id(1).name(requestDTO.getName()).build();
    doReturn(Optional.of(existingCustomer)).when(customerRepository).findByName(requestDTO.getName());

    // act/assert
    assertThrows(AlreadyTakenNameException.class, () -> {
      customerService.validateRegistration(requestDTO);
    });
  }

  @Test
  void validateRegistration_NullEmail_ThrowsInvalidEmailException() {
    // arrange
    CustomerRequestDTO requestDTO = new CustomerRequestDTO();
    requestDTO.setName("John Doe");
    requestDTO.setEmail(null);

    // mock repository to return empty Optional to simulate no existing customer with same name
    doReturn(Optional.empty()).when(customerRepository).findByName(requestDTO.getName());

    // act/assert
    assertThrows(InvalidEmailException.class, () -> {
      customerService.validateRegistration(requestDTO);
    });
  }

  @Test
  public void validateEmail_ValidEmail_ReturnsTrue() {
    // Arrange
    String email = "john@example.com";

    // Act
    boolean result = CustomerServiceImpl.validateEmail(email);

    // Assert
    assertTrue(result);
  }

  @Test
  public void getCustomerById_ValidId_ReturnsCustomer() throws IdNotFoundException {
    // Arrange
    int id = 1;
    Customer expectedCustomer = new Customer();
    expectedCustomer.setId(id);
    expectedCustomer.setName("John Doe");

    Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(expectedCustomer));

    // Act
    Customer actualCustomer = customerService.getCustomerById(id);

    // Assert
    assertEquals(expectedCustomer, actualCustomer);
  }

}
