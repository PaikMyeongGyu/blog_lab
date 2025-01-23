package com.oauth2.login.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@RedisHash(value = "jwt", timeToLive = 60 * 60 * 24 * 7) // 7일
public class RefreshToken {
    private Long userId;
    @Id
    private String value;
}
