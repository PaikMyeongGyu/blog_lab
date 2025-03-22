package com.email.email.member.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Email {
    // 이메일 유효 검사는 CreateRequest 쪽에서 진행할 예정
    private String email;

    private Email(String email) {
        this.email = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }
}
