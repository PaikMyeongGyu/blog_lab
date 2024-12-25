package com.security.spring_security.global.exception.response;

public record ErrorResponse(
        int code,
        String message) {
}