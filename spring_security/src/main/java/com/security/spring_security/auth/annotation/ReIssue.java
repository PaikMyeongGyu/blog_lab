package com.security.spring_security.auth.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Reissue에 대한 정의
 * @Reissue 애노테이션은 인증된 회원 중 아직 리프레시 토큰의 만료 시간이 지나지 않은 회원에게 적용이 된다.
 * 따라서, 만료되었다면 요청에 대해서 거부하고, 만료되지 않았다면 재발급하여 리프레시 토큰을 저장한 뒤 응답으로 내보낸다.
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface ReIssue {
}