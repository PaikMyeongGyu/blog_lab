package com.security.spring_security.user.domain.dto;

public record InactiveUserRequest(
        String email,
        String password
) {
}
