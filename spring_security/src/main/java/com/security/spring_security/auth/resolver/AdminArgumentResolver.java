package com.security.spring_security.auth.resolver;

import com.security.spring_security.auth.annotation.Admin;
import com.security.spring_security.auth.annotation.User;
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

import static com.security.spring_security.global.exception.exceptioncode.ExceptionCode.AUTHORIZATION_IS_EMPTY;
import static com.security.spring_security.global.exception.exceptioncode.ExceptionCode.INVALID_REQUEST;
import static com.security.spring_security.user.domain.UserRole.ADMIN;

/**
 * 이 클래스는 @Admin 애노테이션이 적용된 파라미터를 처리하며,
 * HTTP 요청 헤더(Authorization)에서 엑세스 토큰 정보를 추출하고, 해당 토큰에 대한 검증을 한 뒤
 * 사용자 정보(User 식별자, 권한)을 LoginUser 객체에 담아 반환한다.
 *
 * 주요 역할
 * 1. @Admin 애노테이션 지원 여부
 * 2. HTTP 요청에서 엑세스 토큰을 추출 및 권한 검증
 * 3. 검증된 토큰에서 사용자 정보를 추출해 객체를 생성
 *
 *  @Admin에 대한 정의
 *  @Admin 애노테이션은 인증된 회원 중 권한이 관리자인 회원인 유저에게만 적용이 된다.
 *  관리자 회원은 본인이 작성하지 않은 것들에 관해서 열람, 삭제, 수정에 대한 권한을 가지고 있다.
 *  따라서, 해당 표시가 있다면 위에서 언급한 권한에 대한 검증과 예외 처리를 작성할 필요가 있다.
 */
@Component
@RequiredArgsConstructor
public class AdminArgumentResolver implements HandlerMethodArgumentResolver {
    private final TokenUtils tokenUtils;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Admin.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        String accessToken = webRequest.getHeader(tokenUtils.getAuthHeader());
        tokenUtils.validateToken(accessToken);
        accessToken = tokenUtils.extractBearerToken(accessToken);

        Claims claims = tokenUtils.getClaimsByAccessToken(accessToken);
        Long userId = tokenUtils.getUserIdFromClaims(claims);
        String authority = tokenUtils.getAuthorityFromClaims(claims);
        adminValidation(authority);
        return new LoginUser(userId, authority);
    }

    private void adminValidation(String authority) {
        if (!authority.equals(ADMIN)) {
            throw new AuthException(INVALID_REQUEST);
        }
    }
}