package com.email.email.member.service;

import com.email.email.member.domain.Email;
import com.email.email.member.domain.Member;
import com.email.email.member.domain.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    public void createMember(String email, String name, String password) {
        Member member = new Member(Email.of(email), name, password);
        memberJpaRepository.save(member);
    }

    @Transactional
    public void activeMember(String email) {
        Member findMember = findMemberWith(email);
        memberJpaRepository.activeMemberStatus(findMember.getId());
    }

    @Transactional
    public void deleteMember(String email) {
        Member findMember = findMemberWith(email);
        memberJpaRepository.delete(findMember);
    }

    private Member findMemberWith(String email) {
        return memberJpaRepository.findByEmail(Email.of(email))
                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저를 찾지 못했습니다."));
    }
}
