package com.backend.ecommerce.application.dto.user;

import com.backend.ecommerce.domain.enums.UserRole;

public record RegisterDto(
        String email,
        String name,
        String password
) {
}
