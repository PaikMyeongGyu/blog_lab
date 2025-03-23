package com.email.email.member.presentation.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record JoinMemberRequest(
        @NotNull(message = "이메일에 빈칸은 허용이 되지 않습니다.")
        @Email(message = "올바르지 않은 이메일 형식입니다.")
        String email,
        @NotNull(message = "이름에 빈칸은 허용이 되지 않습니다.")
        String name,
        @NotNull(message = "패스워드에 빈칸은 허용이 되지 않습니다.")
        String password
) {
}
