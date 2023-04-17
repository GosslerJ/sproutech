package com.greenfoxacademy.springwebapp.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.greenfoxacademy.springwebapp.admin.models.Admin;
import com.greenfoxacademy.springwebapp.common.models.ErrorDTO;
import com.greenfoxacademy.springwebapp.customer.repositories.CustomerRepository;
import com.greenfoxacademy.springwebapp.order.models.OrderRequestDTO;
import com.greenfoxacademy.springwebapp.order.repositories.OrderRepository;
import com.greenfoxacademy.springwebapp.order.services.OrderService;
import com.greenfoxacademy.springwebapp.product.models.Product;
import com.greenfoxacademy.springwebapp.product.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.greenfoxacademy.springwebapp.TestFactory.*;
import static com.greenfoxacademy.springwebapp.product.models.ProductStatus.NEW;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerIT {

  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper objectMapper;
  private Authentication auth;
  private Admin testAdmin;
  private OrderService orderService;
  private OrderRepository orderRepository;
  private ProductRepository productRepository;
  private CustomerRepository customerRepository;

  @Autowired
  public OrderControllerIT(OrderService orderService, OrderRepository orderRepository,
                           ProductRepository productRepository, CustomerRepository customerRepository) {
    this.orderService = orderService;
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
    this.customerRepository = customerRepository;
  }

  @BeforeEach
  public void setUp() {
    objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    testAdmin = new Admin();
    auth = new UsernamePasswordAuthenticationToken(testAdmin, null, null);
  }

  @Test
  public void testOrder_shouldReturn201() throws Exception {
    Product product = orderProductBuilder();
    product.setStatus(NEW);
    productRepository.save(product);
    OrderRequestDTO orderRequestDTO = quickOrderRequestDTO();
    List<Product> products = new ArrayList<>();
    products.add(product);
    orderRequestDTO.setOrderedProducts(products);

    mockMvc.perform(post("/api/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(orderRequestDTO))
                    .principal(auth))
            .andExpect(status().isCreated())
            .andReturn();
  }

  @Test
  public void testOrder_shouldReturn404_idNotFound() throws Exception {
    OrderRequestDTO orderRequestDTO = quickOrderFalseRequestDTO();

    MvcResult mvcResult = mockMvc.perform(post("/api/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(orderRequestDTO))
                    .principal(auth))
            .andExpect(status().isNotFound())
            .andReturn();

    ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            ErrorDTO.class);
    assertEquals("error", errorDTO.getStatus());
    assertEquals("Id not found", errorDTO.getMessage());
  }

  @Test
  public void deleteOrder_shouldReturn200() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/order/1004")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(auth))
            .andExpect(status().isOk())
            .andReturn();

    assertThat(mvcResult.getResponse().getContentAsString())
            .isEqualTo("{\"status\":\"ok\",\"message\":\"Order deleted\"}");
    assertThat(orderRepository.findById(1004)).isEmpty();
  }

  @Test
  public void deleteOrder_shouldReturn404_idNotFound() throws Exception {
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/order/1000")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(auth))
            .andExpect(status().isNotFound())
            .andReturn();
    ErrorDTO errorDTO = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            ErrorDTO.class);
    assertEquals("error", errorDTO.getStatus());
    assertEquals("Id not found", errorDTO.getMessage());

  }

}