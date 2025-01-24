package com.oauth2.login.auth.controller;

import com.oauth2.login.auth.domain.UserTokens;
import com.oauth2.login.auth.domain.request.LoginRequest;
import com.oauth2.login.auth.domain.response.AccessTokenResponse;
import com.oauth2.login.auth.service.OAuth2Service;
import com.oauth2.login.common.utils.StringUtils;
import com.oauth2.login.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;

import static com.oauth2.login.common.utils.StringUtils.encodeString;

@RestController
@RequestMapping("/login/oauth2")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private static final String scope = encodeString("openid email profile");

    @Value("${spring.auth.jwt.refresh-token-expiry}")
    private Long refreshTokenExpiry;

    @Value("${oauth2.google.client-id}")
    private String clientId;

    @Value("${oauth2.google.client-secret}")
    private String clientSecret;

    @Value("${oauth2.google.redirect-uri}")
    private String redirectUri;

    @Value("${oauth2.google.token-uri}")
    private String tokenUri;

    @Value("${oauth2.google.resource-uri}")
    private String resourceUri;

    private final UserService userService;
    private final OAuth2Service oAuth2Service;

    @GetMapping
    public void loginWithGoogle(HttpServletResponse response) throws IOException {
        String redirectUri = encodeString(this.redirectUri);
        String googleAuthUrl = StringUtils.format(
                "https://accounts.google.com/o/oauth2/v2/auth?client_id={}&redirect_uri={}&response_type=code&scope={}",
                        clientId, redirectUri, scope
        );

        response.sendRedirect(googleAuthUrl);
    }

    @GetMapping("/code/google")
    public ResponseEntity<AccessTokenResponse> handleGoogleCallback(
            @RequestParam("code") String code,
            HttpServletResponse response
    ) {
        // 로그인 처리 및 UserTokens 생성
        UserTokens userTokens = oAuth2Service.login(code);

        Cookie cookie = new Cookie("refresh-token", userTokens.getRefreshToken());
        cookie.setMaxAge(Math.toIntExact(refreshTokenExpiry)); // 쿠키 만료 시간 설정 (초 단위)
        cookie.setHttpOnly(true); // JavaScript 접근 금지
        cookie.setSecure(false); // HTTPS 환경에서 true로 변경
        cookie.setPath("/"); // 쿠키 경로
        cookie.setDomain("localhost"); // 로컬 환경 도메인 설정

        response.addCookie(cookie);
        response.addHeader("Set-Cookie", String.format(
                "refresh-token=%s; Max-Age=%d; Path=/; Domain=%s; HttpOnly; SameSite=None",
                userTokens.getRefreshToken(),
                refreshTokenExpiry,
                "localhost"
        ));

        return ResponseEntity.ok(new AccessTokenResponse(userTokens.getAccessToken()));
    }

    @PostMapping("/reissue")
    public ResponseEntity<AccessTokenResponse> reissueToken(
            @CookieValue("refresh-token") String refreshToken,
            @RequestHeader("Authorization") String authHeader
    ) {
        String reissuedToken = oAuth2Service.reissueAccessToken(refreshToken, authHeader);
        return ResponseEntity.ok(new AccessTokenResponse(reissuedToken));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(
            @CookieValue("refresh-token") String refreshToken
    ) {

        oAuth2Service.logout(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
