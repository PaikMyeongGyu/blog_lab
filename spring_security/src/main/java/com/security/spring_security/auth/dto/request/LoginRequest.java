package com.security.spring_security.auth.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
