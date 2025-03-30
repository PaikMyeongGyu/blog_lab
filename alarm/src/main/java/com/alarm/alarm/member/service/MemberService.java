package com.alarm.alarm.member.service;

import com.alarm.alarm.common.exception.AlarmException;
import com.alarm.alarm.member.domain.Member;
import org.springframework.stereotype.Service;

import com.alarm.alarm.member.repository.MemberJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    public Long createMember(String name) {
        Member member = Member.of(name);
        memberJpaRepository.save(member);

        return member.getId();
    }

    @Transactional(readOnly = true)
    public Member findMMember(Long memberId) {
        return findMemberBy(memberId);
    }

    private Member findMemberBy(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new AlarmException("존재하지 않는 회원입니다."));
    }
}
