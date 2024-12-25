package com.security.spring_security.global.exception.handler;

import com.security.spring_security.global.exception.AuthException;
import com.security.spring_security.global.exception.UserException;
import com.security.spring_security.global.exception.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(final UserException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(new ErrorResponse(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(final AuthException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(new ErrorResponse(e.getCode(), e.getMessage()));
    }
}
