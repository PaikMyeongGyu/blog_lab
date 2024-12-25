package com.security.spring_security.auth.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @User에 대한 정의
 * 게스트 애노테이션은 인증된 회원 중 권한이 일반 회원인 유저에게만 적용이 된다.
 * 일반 회원은 본인과 관련되지 않은 데이터에 대해 읽기 권한만 가지고 있다.
 * 따라서, 본인이 아닌 사용자의 글을 임의로 삭제하는 요청을 보낸다거나 할 시 금지하는 로직을 반드시 작성해야 한다.
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface User {
}