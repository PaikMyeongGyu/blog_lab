package com.security.spring_security.auth.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Guest에 대한 정의
 * 게스트 애노테이션은 인증된 회원과 인증되지 않은 회원 모두에게 적용이 되는 로직이다.
 * 따라서, 인증되지 않은 회원에 대해선 null로 처리가 되며 해당 값에 맞게 처리를 해주어야 한다.
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Guest {
}