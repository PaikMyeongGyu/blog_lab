package com.security.spring_security.global.exception.exceptioncode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum ExceptionCode {

    // 0번대(기본 공통 코드)
    INVALID_REQUEST(BAD_REQUEST, 1000, "올바르지 않은 요청입니다."),

    // 1000번 대(계정 관련 코드)
    USER_NOT_FOUND(NOT_FOUND, 1000, "해당 정보와 일치하는 계정이 없습니다."),
    USER_NOT_FOUND_WITH_EMAIL(NOT_FOUND, 1001, "이메일 정보와 일치하는 계정이 없습니다."),
    USER_NOT_FOUND_WITH_USERNAME(NOT_FOUND, 1002, "유저 닉네임과 일치하는 계정이 없습니다."),
    USER_DUPLICATE_WITH_EMAIL(NOT_FOUND, 1003, "해당 이메일로 가입된 계정이 있습니다."),
    USER_DUPLICATE_WITH_USERNAME(NOT_FOUND, 1004, "해당 닉네임으로 가입된 계정이 있습니다."),
    PASSWORD_VALIDATION(BAD_REQUEST, 1005, "패스워드가 빈칸 없음, 8-15 글자 사이, 문자, 숫자, 특수문자 포함 조건을 만족하지 않습니다"),
    SESSION_NOT_FOUND(BAD_REQUEST, 1006, "로그인을 다시해주세요."),

    // 2000번 대(인증 관련 코드)
    ACCESS_TOKEN_EXPIRED(BAD_REQUEST, 2001, "AccessToken이 만료되었습니다. 토큰을 재발급 혹은 로그인해주세요."),
    REFRESH_TOKEN_EXPIRED(BAD_REQUEST, 2002, "RefreshToken이 만료되었습니다. 다시 로그인해주세요."),
    AUTHORIZATION_IS_EMPTY(BAD_REQUEST, 2003, "Authorization 토큰 항목이 비어있습니다. Authorization 헤더에 토큰을 추가해주세요."),
    AUTHORIZATION_PARSING_ERROR(BAD_REQUEST, 2004, "유효하지 않은 토큰 정보입니다.");

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
