package com.greenfoxacademy.springwebapp.order.services;

import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.customer.models.Customer;
import com.greenfoxacademy.springwebapp.customer.repositories.CustomerRepository;
import com.greenfoxacademy.springwebapp.order.models.Order;
import com.greenfoxacademy.springwebapp.order.models.OrderRequestDTO;
import com.greenfoxacademy.springwebapp.order.repositories.OrderRepository;
import com.greenfoxacademy.springwebapp.product.models.Product;
import com.greenfoxacademy.springwebapp.product.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static com.greenfoxacademy.springwebapp.TestFactory.productBuilder;
import static com.greenfoxacademy.springwebapp.order.models.OrderStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

  @InjectMocks
  private OrderServiceImpl orderService;
  @Mock
  private CustomerRepository customerRepository;
  @Mock
  private OrderRepository orderRepository;
  @Mock
  private ProductRepository productRepository;

  private Order order;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    orderService = new OrderServiceImpl(customerRepository, orderRepository, productRepository);
    Customer customer = new Customer();
    customer.setId(1);
    order = new Order();
    order.setId(1);
    order.setCustomer(customer);
    order.setStatus(NEW);
    Product product = productBuilder();
    order.setOrderedProducts(Collections.singletonList(product));
    LocalDate orderDate = LocalDate.now();
    LocalDate deliveryDeadline = LocalDate.now().plusDays(7);
    order.setOrderDate(orderDate);
    order.setDeliveryDeadline(deliveryDeadline);
  }

  //  @Test
  //  public void saveOrder_ValidOrderRequestDTO_ReturnsOrderResponseDTO() {
  //    // Arrange
  //    Customer customer = new Customer();
  //    customer.setId(1);
  //    when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
  //
  //    OrderRequestDTO dto = new OrderRequestDTO();
  //    dto.setCustomerId(1);
  //    dto.setOrderDate(LocalDate.now());
  //    dto.setDeliveryDeadline(LocalDate.now().plusDays(7));
  //
  //    List<Product> products = new ArrayList<>();
  //    Product product = new Product();
  //    product.setLength(100.0);
  //    product.setQuantity(10);
  //    product.setQuality("high");
  //    products.add(product);
  //    dto.setOrderedProducts(products);
  //
  //    // Act
  //    OrderResponseDTO result = orderService.saveOrder(dto);
  //
  //    // Assert
  //    assertNotNull(result);
  //    assertEquals(1, result.getCustomerId());
  //    assertEquals(1, result.getId());
  //    assertEquals(products, result.getOrderedProducts());
  //    verify(customerRepository, times(1)).findById(1);
  //    verify(orderRepository, times(1)).save(any(Order.class));
  //    verify(productRepository, times(1)).save(any(Product.class));
  //  }

  @Test
  public void saveOrder_WithInvalidCustomerId() {
    OrderRequestDTO dto = new OrderRequestDTO();
    dto.setCustomerId(999);

    Exception exception = assertThrows(IdNotFoundException.class, () -> {
      orderService.saveOrder(dto);
    });

    String expectedMessage = "Id not found";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void testDeleteOrderById() throws IdNotFoundException {
    Integer orderId = 1;
    Optional<Order> optionalOrder = Optional.of(order);
    doReturn(optionalOrder).when(orderRepository).findById(orderId);

    orderService.deleteOrderById(orderId);

    verify(orderRepository).delete(order);
  }

  @Test
  public void testDeleteOrderByIdThrowsException() {
    Integer orderId = 1;
    doThrow(IdNotFoundException.class).when(orderRepository).findById(orderId);

    assertThrows(IdNotFoundException.class, () -> orderService.deleteOrderById(orderId));
    verify(orderRepository, never()).delete(any(Order.class));
  }

  @Test
  public void testFilterOrdersByDeliveryDeadline_withNullDays_shouldReturnAllOrders() {
    List<Order> orders = Arrays.asList(
            order.builder().id(1001).status(NEW).orderDate(LocalDate.now()).deliveryDeadline(LocalDate.now()
                    .plusDays(100)).build(),
            order.builder().id(1002).status(READY).orderDate(LocalDate.now()).deliveryDeadline(LocalDate.now()
                    .plusDays(3)).build(),
            order.builder().id(1003).status(IN_PROGRESS).orderDate(LocalDate.now()).deliveryDeadline(LocalDate.now()
                    .plusDays(3)).build()
    );
    when(orderRepository.findAll()).thenReturn(orders);

    List<Order> result = orderService.filterOrdersByDeliveryDeadline(null);

    assertEquals(3, result.size());
    assertTrue(result.containsAll(orders));
  }

  //    @Test
  //    public void testFilterOrdersByDeliveryDeadline_withValidDays_shouldReturnFilteredOrders() {
  //      List<Order> orders = Arrays.asList(
  //              order.builder().id(1001).status(NEW).orderDate(LocalDate.now()).deliveryDeadline(LocalDate.now()
  //              .plusDays(100)).build(),
  //              order.builder().id(1002).status(READY).orderDate(LocalDate.now()).deliveryDeadline(LocalDate.now()
  //              .plusDays(3)).build(),
  //              order.builder().id(1003).status(IN_PROGRESS).orderDate(LocalDate.now())
  //              .deliveryDeadline(LocalDate.now().plusDays(3)).build()
  //      );
  //      when(orderRepository
  //              .findAllByDeliveryDeadlineLessThanEqualAndStatusIn(LocalDate.now().plusDays(5),
  //              Arrays.asList(NEW, IN_PROGRESS)))
  //              .thenReturn(orders.subList(0, 2));
  //
  //      List<Order> result = orderService.filterOrdersByDeliveryDeadline(100);
  //
  //      assertEquals(1, result.size());
  //      assertTrue(result.containsAll(orders.subList(0, 2)));
  //    }

  @Test
  public void testFilterOrdersByDeliveryDeadline_withValidDays_shouldReturnEmptyList() {
    when(orderRepository
            .findAllByDeliveryDeadlineLessThanEqualAndStatusIn(LocalDate.now()
                    .plusDays(1), Arrays.asList(NEW, IN_PROGRESS))).thenReturn(Collections.emptyList());

    List<Order> result = orderService.filterOrdersByDeliveryDeadline(1);

    assertTrue(result.isEmpty());
  }

}
