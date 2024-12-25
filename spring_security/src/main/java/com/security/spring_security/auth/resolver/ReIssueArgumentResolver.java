package com.security.spring_security.auth.resolver;

import com.security.spring_security.auth.annotation.ReIssue;
import com.security.spring_security.auth.dto.response.TokenResponse;
import com.security.spring_security.auth.service.AuthService;
import com.security.spring_security.auth.util.TokenUtils;
import com.security.spring_security.user.domain.UserRole;
import com.security.spring_security.user.domain.Users;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.security.spring_security.auth.util.TokenUtils.AUTHORITIES;
import static com.security.spring_security.auth.util.TokenUtils.USER_ID;
import static com.security.spring_security.user.domain.UserRole.ADMIN;
import static com.security.spring_security.user.domain.UserRole.USER;

/**
 * 이 클래스는 @Reissue 애노테이션이 적용된 파라미터를 처리하며,
 * HTTP 요청 헤더(Authorization)에서 리프레시 토큰 정보를 추출하고, 해당 토큰에 대한 검증을 한 뒤
 * 새로운 엑세스 토큰과 리프레시 토큰을 발급한 뒤, Redis에 저장된 리프레시 토큰을 변경한다.
 *
 * 주요 역할
 * 1. @Reissue 애노테이션 지원 여부
 * 2. HTTP 요청에서 엑세스 토큰을 추출 및 검증
 * 3. 검증된 토큰에서 사용자 정보를 추출해 토큰 재발급 및 InMemory DB에 저장
 *
 * @Reissue에 대한 정의
 * @Reissue 애노테이션은 인증된 회원 중 아직 리프레시 토큰의 만료 시간이 지나지 않은 회원에게 적용이 된다.
 * 따라서, 만료되었다면 요청에 대해서 거부하고, 만료되지 않았다면 재발급하여 리프레시 토큰을 저장한 뒤 응답으로 내보낸다.
 */
@Component
@RequiredArgsConstructor
public class ReIssueArgumentResolver implements HandlerMethodArgumentResolver {
    private final TokenUtils tokenUtils;
    private final AuthService authService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ReIssue.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        String refreshToken = webRequest.getHeader(tokenUtils.getAuthHeader());
        tokenUtils.validateToken(refreshToken);
        refreshToken = tokenUtils.extractBearerToken(refreshToken);
        authService.validateRefreshToken(refreshToken);

        Claims claims = tokenUtils.getClaimsByRefreshToken(refreshToken);
        Long userId = Long.parseLong(claims.get(USER_ID).toString());
        UserRole userRole = claims.get(AUTHORITIES).equals(ADMIN) ? ADMIN : USER;

        Users simpleUser = Users.simpleUser(userId, userRole);
        String newAccessToken = tokenUtils.generateAccessToken(simpleUser);
        String newRefreshToken = tokenUtils.generateRefreshToken(simpleUser);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
}
