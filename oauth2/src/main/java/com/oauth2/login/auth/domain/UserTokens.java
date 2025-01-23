package com.oauth2.login.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserTokens {
    private final String accessToken;
    private final String refreshToken;
}
