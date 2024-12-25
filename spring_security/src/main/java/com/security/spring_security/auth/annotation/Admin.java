package com.security.spring_security.auth.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *  @Admin에 대한 정의
 *  @Admin 애노테이션은 인증된 회원 중 권한이 관리자인 회원인 유저에게만 적용이 된다.
 *  관리자 회원은 본인이 작성하지 않은 것들에 관해서 열람, 삭제, 수정에 대한 권한을 가지고 있다.
 *  따라서, 해당 표시가 있다면 위에서 언급한 권한에 대한 검증과 예외 처리를 작성할 필요가 있다.
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Admin {
}