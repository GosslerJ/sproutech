package com.greenfoxacademy.springwebapp.customer.services;

import com.greenfoxacademy.springwebapp.common.exceptions.AlreadyTakenNameException;
import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.common.exceptions.InvalidEmailException;
import com.greenfoxacademy.springwebapp.customer.models.Customer;
import com.greenfoxacademy.springwebapp.customer.models.CustomerRequestDTO;
import com.greenfoxacademy.springwebapp.customer.models.CustomerResponseDTO;
import com.greenfoxacademy.springwebapp.customer.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

  //      @Test
  //      public void saveCustomer_ValidInput_CustomerSaved() throws AlreadyTakenNameException, InvalidEmailException {
  //        doNothing().when(customerService).validateRegistration(any());
  //        CustomerRequestDTO requestDTO = customerRequestDtoBuilder();
  //        CustomerResponseDTO responseDTO = customerService.saveCustomer(requestDTO);
  //        assertNotNull(responseDTO);
  //      }

  @Test
  public void saveCustomer_DuplicateName_ThrowsAlreadyTakenNameException() {
    CustomerRequestDTO requestDTO = new CustomerRequestDTO();
    requestDTO.setName("John Doe");
    requestDTO.setEmail("johndoe@example.com");

    doThrow(AlreadyTakenNameException.class).when(customerRepository).save(any(Customer.class));

    assertThrows(AlreadyTakenNameException.class, () -> {
      customerService.saveCustomer(requestDTO);
    });
  }

  //  @Test
  //  public void saveCustomer_InvalidEmail_ThrowsInvalidEmailException() {
  //    CustomerRequestDTO requestDTO = customerWithInvalidEmail();
  //
  //    assertThrows(InvalidEmailException.class, () -> {
  //      customerService.saveCustomer(requestDTO);
  //    });
  //  }

  //  @Test
  //  public void saveCustomer_ValidInput_ReturnsCorrectResponseDTO()
  //          throws AlreadyTakenNameException, InvalidEmailException {
  //    CustomerRequestDTO requestDTO = customerRequestDtoBuilder();
  //    Customer customer = new Customer();
  //    customer.setId(any());
  //    customer.setName(requestDTO.getName());
  //    CustomerResponseDTO expectedResponseDTO = CustomerResponseDTO.builder()
  //            .id(customer.getId())
  //            .name(customer.getName())
  //            .build();
  //    assertEquals(expectedResponseDTO, customerService.saveCustomer(requestDTO));
  //  }

  @Test
  void validateRegistration_NullEmail_ThrowsInvalidEmailException() {
    CustomerRequestDTO requestDTO = new CustomerRequestDTO();
    requestDTO.setName("John Doe");
    requestDTO.setEmail(null);

    doReturn(Optional.empty()).when(customerRepository).findByName(requestDTO.getName());

    assertThrows(InvalidEmailException.class, () -> {
      customerService.validateRegistration(requestDTO);
    });
  }

  @Test
  void validateRegistration_InvalidEmail_ThrowsInvalidEmailException() {
    CustomerRequestDTO requestDTO = new CustomerRequestDTO();
    requestDTO.setName("John Doe");
    requestDTO.setEmail("invalid_email");

    doReturn(Optional.empty()).when(customerRepository).findByName(requestDTO.getName());

    assertThrows(InvalidEmailException.class, () -> {
      customerService.validateRegistration(requestDTO);
    });
  }

  @Test
  void validateRegistration_ShortEmail_ThrowsInvalidEmailException() {
    CustomerRequestDTO requestDTO = new CustomerRequestDTO();
    requestDTO.setName("John Doe");
    requestDTO.setEmail("a@b.c");

    doReturn(Optional.empty()).when(customerRepository).findByName(requestDTO.getName());

    assertThrows(InvalidEmailException.class, () -> {
      customerService.validateRegistration(requestDTO);
    });
  }


  @Test
  public void validateEmail_ValidEmail_ReturnsTrue() {
    String email = "john@example.com";

    boolean result = CustomerServiceImpl.validateEmail(email);

    assertTrue(result);
  }

  @Test
  public void validateEmail_ValidEmail_ReturnsFalse() {
    String email = "johnexample.com";

    boolean result = CustomerServiceImpl.validateEmail(email);

    assertFalse(result);
  }

  @Test
  public void getCustomerById_ValidId_ReturnsCustomer() throws IdNotFoundException {
    int id = 1;
    Customer expectedCustomer = new Customer();
    expectedCustomer.setId(id);
    expectedCustomer.setName("John Doe");

    when(customerRepository.findById(id)).thenReturn(Optional.of(expectedCustomer));

    Customer actualCustomer = customerService.getCustomerById(id);

    assertEquals(expectedCustomer, actualCustomer);
  }

  @Test
  public void testConvert_withValidCustomer_shouldReturnValidCustomerResponseDTO() {
    Customer customer = new Customer();
    customer.setId(1);
    customer.setName("John");

    CustomerResponseDTO expected = CustomerResponseDTO.builder()
            .id(customer.getId())
            .name(customer.getName())
            .build();

    CustomerResponseDTO actual = customerService.convert(customer);

    assertEquals(expected, actual);
  }

  @Test
  public void testConvert_withNullCustomer_shouldThrowNullPointerException() {
    Customer customer = null;

    assertThrows(NullPointerException.class, () -> customerService.convert(customer));
  }

  @Test
  public void testConvert_withCustomerWithNullName_shouldReturnCustomerResponseDtoWithNullName() {
    Customer customer = new Customer();
    customer.setId(1);
    customer.setName(null);

    CustomerResponseDTO expected = CustomerResponseDTO.builder()
            .id(customer.getId())
            .name(null)
            .build();

    CustomerResponseDTO actual = customerService.convert(customer);

    assertEquals(expected, actual);
  }

}
