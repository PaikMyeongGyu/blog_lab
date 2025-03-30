package com.alarm.alarm.member.presentation;

import com.alarm.alarm.member.domain.Member;
import com.alarm.alarm.member.presentation.request.MemberCreateRequest;
import com.alarm.alarm.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Long> createMember(
            @RequestBody MemberCreateRequest req
    ) {
        Long memberId = memberService.createMember(req.name());
        return ResponseEntity.status(CREATED).body(memberId);
    }

    @GetMapping
    public ResponseEntity<Member> getMember(
            @RequestParam Long memberId
    ) {
        Member findMember = memberService.findMMember(memberId);
        return ResponseEntity.ok(findMember);
    }
}
