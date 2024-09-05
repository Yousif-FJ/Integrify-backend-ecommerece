package com.backend.ecommerce.application.dto;

import java.util.UUID;

public record CreateOrderProductDto(
        UUID product_id
) {
}
