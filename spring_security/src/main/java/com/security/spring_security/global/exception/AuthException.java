package com.security.spring_security.global.exception;

import com.security.spring_security.global.exception.exceptioncode.ExceptionCode;

public class AuthException extends BaseException {
    public AuthException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
