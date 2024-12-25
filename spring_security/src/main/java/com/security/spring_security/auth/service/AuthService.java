package com.security.spring_security.auth.service;

import com.security.spring_security.auth.dto.response.TokenResponse;
import com.security.spring_security.auth.util.TokenUtils;
import com.security.spring_security.global.exception.AuthException;
import com.security.spring_security.global.exception.exceptioncode.ExceptionCode;
import com.security.spring_security.user.domain.Users;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

import static com.security.spring_security.global.exception.exceptioncode.ExceptionCode.INVALID_REQUEST;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenUtils tokenUtils;
    private final RedisTemplate redisTemplate;
    private static final String REFRESH_TOKEN_KEY_PREFIX = "refreshToken:";

    public TokenResponse createTokenByUserInfo(
            Users user,
            long refreshTokenExpirationTime
    ) {
        String accessToken = tokenUtils.generateAccessToken(user);
        String refreshToken = tokenUtils.generateRefreshToken(user);

        redisTemplate.opsForValue().set(REFRESH_TOKEN_KEY_PREFIX + user.getId(),
                                            refreshToken,
                                            refreshTokenExpirationTime, TimeUnit.MILLISECONDS);
        return new TokenResponse(accessToken, refreshToken);
    }

    public void deleteTokenByUserId(Long userId) {
        redisTemplate.delete(REFRESH_TOKEN_KEY_PREFIX + userId);
    }

    public void updateRedisToken(String refreshToken, Long refreshTokenExpirationTime) {
        Claims claims = tokenUtils.getClaims(refreshToken);
        Long userId = tokenUtils.getUserIdFromClaims(claims);
        redisTemplate.opsForValue().set(REFRESH_TOKEN_KEY_PREFIX + userId,
                                            refreshToken,
                                            refreshTokenExpirationTime, TimeUnit.MILLISECONDS);
    }

    public void validateRefreshToken(String refreshToken) {
        Claims claims = tokenUtils.getClaimsByRefreshToken(refreshToken);
        Long userId = tokenUtils.getUserIdFromClaims(claims);

        String storedRefreshToken = (String) redisTemplate.opsForValue().get(REFRESH_TOKEN_KEY_PREFIX + userId);
        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new AuthException(INVALID_REQUEST);
        }
    }
}
