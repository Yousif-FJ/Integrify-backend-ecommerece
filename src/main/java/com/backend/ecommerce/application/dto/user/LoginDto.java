package com.backend.ecommerce.application.dto.user;

public record LoginDto(
        String email,
        String password
) {
}
