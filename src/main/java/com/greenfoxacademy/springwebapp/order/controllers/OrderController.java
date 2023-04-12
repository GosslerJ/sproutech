package com.greenfoxacademy.springwebapp.order.controllers;

import com.greenfoxacademy.springwebapp.common.exceptions.IdNotFoundException;
import com.greenfoxacademy.springwebapp.common.models.StatusResponseDTO;
import com.greenfoxacademy.springwebapp.material.models.MaterialResponseDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Tag(name = "Order", description = "Order related endpoints")
@RestController
public class OrderController {
  private OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @Operation(summary = "Order", description = "Add new Order")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "201", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = OrderResponseDTO.class))),
          //          @ApiResponse(responseCode = "409", description = "name is already taken",
          //                  content = @Content(mediaType = "application/json",
          //                          schema = @Schema(implementation = StatusResponseDTO.class))),
          //          TODO: scenarios?
  })
  @PostMapping("/order")
  public ResponseEntity<?> order(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
    //            try {
    OrderResponseDTO createdOrder = orderService.saveOrder(orderRequestDTO);
    return ResponseEntity.status(CREATED).body(createdOrder);
    //                } catch (Exception e) {
    //                return ResponseEntity.status(CONFLICT).body(new StatusResponseDTO("error", e.getMessage()));
    //            }
  }

  @Operation(summary = "Delete Order", description = "Delete and existing order by id")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "successful operation",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = OrderResponseDTO.class))),
          @ApiResponse(responseCode = "404", description = "invalid id",
                  content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = StatusResponseDTO.class))),
          //          TODO: scenarios?
  })
  @DeleteMapping("/order/{id}")
  public ResponseEntity<?> deleteOrder(@Valid @PathVariable Integer id) {
    try {
      orderService.deleteOrderById(id);
      return ResponseEntity.status(OK).body(new StatusResponseDTO("ok", "Order deleted"));
    } catch (IdNotFoundException e) {
      return ResponseEntity.status(404).body(new StatusResponseDTO("error", e.getMessage()));
    }
  }

    @Operation(summary = "List close deadlines", description = "Get orders with close delivery deadlines")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDTO.class))),
    })
    @GetMapping("/order")
    public ResponseEntity<?> filterOrders(@Valid @RequestParam Integer days) {
      List<Order> orders = orderService.filterOrdersByDeliveryDeadline(days);
        return ResponseEntity.status(OK).body(orders);
    }

}
