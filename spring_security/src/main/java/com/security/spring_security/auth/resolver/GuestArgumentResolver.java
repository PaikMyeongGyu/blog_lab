package com.security.spring_security.auth.resolver;

import com.security.spring_security.auth.annotation.Guest;
import com.security.spring_security.auth.dto.LoginUser;
import com.security.spring_security.auth.util.TokenUtils;
import com.security.spring_security.global.exception.AuthException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.security.spring_security.auth.util.TokenUtils.*;
import static com.security.spring_security.global.exception.exceptioncode.ExceptionCode.AUTHORIZATION_IS_EMPTY;
import static com.security.spring_security.global.exception.exceptioncode.ExceptionCode.INVALID_REQUEST;

/**
 * 이 클래스는 @Guest 애노테이션이 적용된 파라미터를 처리하며,
 * HTTP 요청 헤더(Authorization)에서 엑세스 토큰 정보를 추출하고, 해당 토큰에 대한 검증을 한 뒤
 * 사용자 정보(User 식별자, 권한)을 LoginUser 객체에 담아 반환한다.
 *
 * 주요 역할
 * 1. @Guest 애노테이션 지원 여부
 * 2. HTTP 요청에서 엑세스 토큰을 추출 및 검증
 * 3. 검증된 토큰에서 사용자 정보를 추출해 객체를 생성
 *
 * @Guest에 대한 정의
 * 게스트 애노테이션은 인증된 회원과 인증되지 않은 회원 모두에게 적용이 되는 로직이다.
 * 따라서, 인증되지 않은 회원에 대해선 null로 처리가 되며 해당 값에 맞게 처리를 해주어야 한다.
 */
@Component
@RequiredArgsConstructor
public class GuestArgumentResolver implements HandlerMethodArgumentResolver {
    private final TokenUtils tokenUtils;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Guest.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        String accessToken = webRequest.getHeader(tokenUtils.getAuthHeader());
        if (accessToken == null || accessToken.isBlank()) {
            return null;
        }
        tokenUtils.validateToken(accessToken);
        accessToken = tokenUtils.extractBearerToken(accessToken);

        Claims claims = tokenUtils.getClaimsByAccessToken(accessToken);
        Long userId = tokenUtils.getUserIdFromClaims(claims);
        String authority = tokenUtils.getAuthorityFromClaims(claims);
        return new LoginUser(userId, authority);
    }
}