package com.security.spring_security.global.exception;

import com.security.spring_security.global.exception.exceptioncode.ExceptionCode;

public class UserException extends BaseException {
    public UserException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
