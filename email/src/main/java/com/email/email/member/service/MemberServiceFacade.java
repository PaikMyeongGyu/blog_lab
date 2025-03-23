package com.email.email.member.service;

import com.email.email.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberServiceFacade {
    private final MemberService memberService;
    private final EmailService emailService;

    public void createMember(String email, String name, String password, String token) {
        memberService.createMember(email, name, password);
        emailService.sendVerificationEmail(email, token);
    }
}
