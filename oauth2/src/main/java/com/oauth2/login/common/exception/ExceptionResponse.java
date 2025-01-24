package com.oauth2.login.common.exception;

public record ExceptionResponse(
        int code,
        String message
) {
}
