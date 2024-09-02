package com.backend.ecommerce.application.dto.user;

import com.backend.ecommerce.domain.enums.UserRole;

import java.util.UUID;

public record UserDto (UUID id, String name, String email, UserRole userRole){
}
