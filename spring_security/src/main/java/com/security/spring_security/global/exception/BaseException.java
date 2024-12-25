package com.security.spring_security.global.exception;

import com.security.spring_security.global.exception.exceptioncode.ExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    public BaseException(ExceptionCode exceptionCode) {
        this.httpStatus = exceptionCode.getHttpStatus();
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}