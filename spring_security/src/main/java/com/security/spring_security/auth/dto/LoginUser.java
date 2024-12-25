package com.security.spring_security.auth.dto;

public record LoginUser(
        Long userId,
        String role
) {
}
