package com.security.spring_security.auth.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
