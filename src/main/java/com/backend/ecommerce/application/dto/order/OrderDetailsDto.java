package com.backend.ecommerce.application.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderDetailsDto(
  UUID id,
  UUID userId,
  UUID paymentId,
  String userName,
  String userEmail,
  String orderStatus,
  String shipmentCity,
  String shipmentStreet,
  String shipmentPostNumber,
  LocalDate orderDate,
  float amount,
  String billingCity,
  String billingStreet,
  String billingPostNumber,
  boolean paymentStatus
){};