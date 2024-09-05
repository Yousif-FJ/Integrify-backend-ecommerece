package com.backend.ecommerce.abstraction;

import com.backend.ecommerce.application.dto.order.CreateOrderDto;
import com.backend.ecommerce.application.dto.order.OrderDetailsDto;
import com.backend.ecommerce.application.dto.order.OrderUpdateDto;
import com.backend.ecommerce.application.dto.dtoInterfaces.IOrderDto;
import com.backend.ecommerce.domain.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
  public List<IOrderDto> getAllOrders();
  public List<IOrderDto> getOrdersByUserId(UUID id);
  public Optional<OrderDetailsDto> findOrder(UUID id, User user);
  public Optional<OrderDetailsDto> createNewOrder(CreateOrderDto createOrderDto, User user);
  public boolean deleteOrder(UUID id);
  public Optional<OrderDetailsDto> updateOrder(UUID id, OrderUpdateDto orderUpdate);
}
