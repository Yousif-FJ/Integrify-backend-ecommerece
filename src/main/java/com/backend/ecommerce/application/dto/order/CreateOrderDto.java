package com.backend.ecommerce.application.dto.order;

import com.backend.ecommerce.application.dto.CreateOrderProductDto;
import com.backend.ecommerce.application.dto.payment.UpdatePaymentDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;


public record CreateOrderDto (
  String city,
  String street,
  String postNumber,
  String status,
  List<UUID> productsId
){}
