package com.greenfoxacademy.springwebapp.order.controllers;

import com.greenfoxacademy.springwebapp.common.models.StatusResponseDTO;
import com.greenfoxacademy.springwebapp.order.models.Order;
import com.greenfoxacademy.springwebapp.order.models.OrderRequestDTO;
import com.greenfoxacademy.springwebapp.order.models.OrderResponseDTO;
import com.greenfoxacademy.springwebapp.order.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Order", description = "Order related endpoints")
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderController {
  private OrderService orderService;

  @Operation(summary = "Order", description = "Add new Order")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = OrderResponseDTO.class))),
  })
  @PostMapping("/order")
  public ResponseEntity<?> order(@RequestBody OrderRequestDTO orderRequestDTO) {
    OrderResponseDTO createdOrder = orderService.saveOrder(orderRequestDTO);
    return ResponseEntity.status(CREATED).body(createdOrder);
  }

  @Operation(summary = "Delete Order", description = "Delete an existing order by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = OrderResponseDTO.class))),
  })
  @DeleteMapping("/order/{id}")
  public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
    orderService.deleteOrderById(id);
    return ResponseEntity.status(OK).body(new StatusResponseDTO("ok", "Order deleted"));
  }

  @Operation(summary = "List close deadlines", description = "Get orders with close delivery deadlines")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = OrderResponseDTO.class))),
  })
  @GetMapping("/order")
  public ResponseEntity<?> filterOrders(@RequestParam Integer days) {
    List<Order> orders = orderService.filterOrdersByDeliveryDeadline(days);
    return ResponseEntity.status(OK).body(orders);
  }

}
