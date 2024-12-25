package com.security.spring_security.user.domain.dto;

public record JoinUserRequest(
        String username,
        String email,
        String password
) {
}
