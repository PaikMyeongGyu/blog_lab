package com.oauth2.login.user.domain.request;

public record UserUpdateRequest(
        String name,
        String nickname,
        String profileUri
) {
}
