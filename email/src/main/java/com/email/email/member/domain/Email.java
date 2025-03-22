package com.email.email.member.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Email {
    @NotNull(message = "이메일은 빈 칸이 허용이 되지 않습니다.")
    @jakarta.validation.constraints.Email(message = "이메일 양식이 올바르지 않습니다.")
    private String email;

    private Email(String email) {
        this.email = email;
    }

    public Email of(String email) {
        return new Email(email);
    }
}
