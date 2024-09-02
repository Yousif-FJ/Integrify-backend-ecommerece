package com.backend.ecommerce.application.dto.user;

public record AuthResultDto(String token, UserDto user) {
}
