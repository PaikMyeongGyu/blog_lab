package com.security.spring_security.auth.util;

import com.security.spring_security.global.exception.AuthException;
import com.security.spring_security.user.domain.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;

import static com.security.spring_security.global.exception.exceptioncode.ExceptionCode.AUTHORIZATION_IS_EMPTY;
import static com.security.spring_security.global.exception.exceptioncode.ExceptionCode.INVALID_REQUEST;
import static io.jsonwebtoken.lang.Strings.UTF_8;

@Component
public class TokenUtils {

    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 30 * 60 * 1000;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 24 * 30 * 60 * 1000;
    public static final String USER_ID = "userId";
    public static final String AUTHORITIES = "authorities";
    public static final String TOKEN_TYPE = "tokenType";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String BEARER = "Bearer ";

    @Getter
    private final SecretKey secretKey;

    @Getter
    private final String authHeader;

    public TokenUtils(
            @Value("#{environment['app.secret-key']}") String secretKey,
            @Value("#{environment['app.auth-header']}") String authHeader
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(UTF_8));
        this.authHeader = authHeader;
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody();
    }

    public Long getUserIdFromClaims(Claims claims) {
        return Long.parseLong(claims.get(USER_ID).toString());
    }

    public String getAuthorityFromClaims(Claims claims) {
        return claims.get(AUTHORITIES).toString();
    }

    public String generateRefreshToken(Users user) {
        return Jwts.builder()
                .claim(USER_ID, user.getId())
                .claim(AUTHORITIES, user.getRole())
                .claim(TOKEN_TYPE, REFRESH_TOKEN)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(secretKey).compact();
    }

    public String generateAccessToken(Users user) {
        return Jwts.builder()
                .claim(USER_ID, user.getId())
                .claim(AUTHORITIES, user.getRole())
                .claim(TOKEN_TYPE, ACCESS_TOKEN)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(secretKey).compact();
    }

    public Claims getClaimsByAccessToken(String token) {
        Claims claims = getClaims(token);
        accessTokenValidationWith(claims);
        return claims;
    }

    public void accessTokenValidationWith(Claims claims) {
        if(claims.get(TOKEN_TYPE) == null || !claims.get(TOKEN_TYPE).toString().equals(ACCESS_TOKEN)) {
            throw new AuthException(INVALID_REQUEST);
        }
    }

    public Claims getClaimsByRefreshToken(String token) {
        Claims claims = getClaims(token);
        refreshTokenValidationWith(claims);
        return claims;
    }

    public void refreshTokenValidationWith(Claims claims) {
        if (claims.get(TOKEN_TYPE) == null || !claims.get(TOKEN_TYPE).toString().equals(REFRESH_TOKEN)) {
            throw new AuthException(INVALID_REQUEST);
        }
    }

    public void validateToken(String token) {
        if (token == null || token.isBlank()) {
            throw new AuthException(AUTHORIZATION_IS_EMPTY);
        }
        authorizationFormatChecker(token);
    }

    public String extractBearerToken(String token) {
        return token.substring(BEARER.length());
    }

    private void authorizationFormatChecker(String token) {
        if (token == null || !token.startsWith(BEARER)) {
            throw new AuthException(INVALID_REQUEST);
        }
    }
}