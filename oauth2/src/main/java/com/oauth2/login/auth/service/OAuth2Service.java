package com.oauth2.login.auth.service;

import com.oauth2.login.auth.JwtUtil;
import com.oauth2.login.auth.domain.RefreshToken;
import com.oauth2.login.auth.domain.UserTokens;
import com.oauth2.login.auth.domain.request.LoginRequest;
import com.oauth2.login.auth.infrastructure.GoogleOAuthProvider;
import com.oauth2.login.auth.repository.RefreshTokenRepository;
import com.oauth2.login.common.exception.ExceptionCode;
import com.oauth2.login.common.exception.InvalidJwtException;
import com.oauth2.login.user.domain.User;
import com.oauth2.login.user.domain.request.UserCreateOrLoginRequest;
import com.oauth2.login.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class OAuth2Service {
    private final GoogleOAuthProvider googleOAuthProvider;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    public UserTokens login(String code) {
        var userCreateOrLoginRequest = googleOAuthProvider.getUserInfoByGoogle(code);

        User user = findOrCreateUser(userCreateOrLoginRequest);

        UserTokens userTokens = jwtUtil.createLoginToken(user.getId().toString());
        RefreshToken refreshToken = new RefreshToken(user.getId(), userTokens.getRefreshToken());
        refreshTokenRepository.save(refreshToken);
        return userTokens;
    }

    public void logout(String refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
    }

    public String reissueAccessToken(String refreshToken, String authHeader) {
        //Bearer 제거
        String accessToken = authHeader.split(" ")[1];

        //토큰 만료, 비밀키 무결성 검사
        jwtUtil.validateRefreshToken(refreshToken);

        //Access Token이 유효한 경우 -> 재반환
        if (jwtUtil.isAccessTokenValid(accessToken)) {
            return accessToken;
        }

        //Access Token이 만료된 경우 -> Refresh Token DB 검증 후 재발급
        if (jwtUtil.isAccessTokenExpired(accessToken)) {
            RefreshToken foundRefreshToken = refreshTokenRepository.findById(refreshToken)
                    .orElseThrow(() -> new InvalidJwtException(ExceptionCode.INVALID_REFRESH_TOKEN));
            return jwtUtil.reissueAccessToken(foundRefreshToken.getUserId().toString(), "accessToken");
        }

        throw new InvalidJwtException(ExceptionCode.FAILED_TO_VALIDATE_TOKEN);
    }

    private User findOrCreateUser(UserCreateOrLoginRequest request) {
        return userService
                .findUserByEmail(request.email())
                .orElseGet(() -> createUser(request));
    }

    private User createUser(UserCreateOrLoginRequest request) {
        User user = User.builder()
                .email(request.email())
                .name(request.name())
                .nickname(request.nickname())
                .profileUri(request.profileUri())
                .build();

        userService.saveUser(user);
        return user;
    }
}
