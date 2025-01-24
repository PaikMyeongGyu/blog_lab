package com.oauth2.login.user.domain.request;

public record UserCreateOrLoginRequest(
        String email,
        String name,
        String nickname,
        String profileUri
) {
}
