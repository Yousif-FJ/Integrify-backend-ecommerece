package com.backend.ecommerce.presentation;

import com.backend.ecommerce.abstraction.OrderService;
import com.backend.ecommerce.application.dto.dtoInterfaces.IOrderDto;
import com.backend.ecommerce.application.dto.order.CreateOrderDto;
import com.backend.ecommerce.application.dto.order.OrderDetailsDto;
import com.backend.ecommerce.application.dto.order.OrderUpdateDto;
import com.backend.ecommerce.domain.entities.User;
import com.backend.ecommerce.domain.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  @Autowired
  private OrderService orderService;


  @GetMapping
  public List<IOrderDto> findAll(Authentication authentication) {
    var user = ((User)authentication.getPrincipal());
    if (user.getUserRole() == UserRole.user){
      return orderService.getOrdersByUserId(user.getId());
    } else {
      return orderService.getAllOrders();
    }
  }

  //For testing purposes
  @GetMapping("exception")
  public ResponseEntity<String> exception(Authentication authentication) {
    var user = ((User)authentication.getPrincipal());
    throw new RuntimeException("Something went wrong user was " + user.getUsername());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<OrderDetailsDto>> findOne(@PathVariable UUID id,
                                                           Authentication authentication) {
    var user = ((User)authentication.getPrincipal());

    Optional<OrderDetailsDto> order = orderService.findOrder(id, user);
    if (order.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(order);
  }

  @PostMapping
  public ResponseEntity<Optional<OrderDetailsDto>> createNewOrder(@RequestBody CreateOrderDto createOrderDto,
                                                                  Authentication authentication){
    if (authentication == null || !authentication.isAuthenticated()){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    var user = ((User)authentication.getPrincipal());

    Optional<OrderDetailsDto> newOrder = orderService.createNewOrder(createOrderDto, user);

    if (newOrder.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(newOrder);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Optional<OrderDetailsDto>> updateOrder(@PathVariable UUID id, @RequestBody OrderUpdateDto orderUpdate) {
    Optional<OrderDetailsDto> updatedOrder = orderService.updateOrder(id, orderUpdate);
    if (updatedOrder.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(updatedOrder);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
    boolean answer = orderService.deleteOrder(id);
    if (answer) return new ResponseEntity<>(HttpStatus.OK);
    return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
  }
}
