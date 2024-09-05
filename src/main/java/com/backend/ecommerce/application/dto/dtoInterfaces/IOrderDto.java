package com.backend.ecommerce.application.dto.dtoInterfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public interface IOrderDto {
  UUID getId();
  UUID getUserId();
  String getUserName();
  String getOrderStatus();
  boolean getPaymentStatus();
  LocalDate getOrderDate();
  float getAmount();
}
