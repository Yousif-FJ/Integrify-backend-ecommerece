package com.backend.ecommerce.application.dto.order;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderDto(
        UUID id,
        UUID userId,
        String userName,
        String orderStatus,
        boolean paymentStatus,
        LocalDateTime orderDate,
        float amount
) { }
