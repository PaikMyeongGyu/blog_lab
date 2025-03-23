package com.email.email.member.presentation;

import com.email.email.email.service.EmailService;
import com.email.email.member.domain.Email;
import com.email.email.member.presentation.request.JoinMemberRequest;
import com.email.email.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> joinMember(
        @RequestBody JoinMemberRequest req
    ) {
        memberService.createMember(req.email(), req.name(), req.password());
        return ResponseEntity.ok().build();
    }
}
