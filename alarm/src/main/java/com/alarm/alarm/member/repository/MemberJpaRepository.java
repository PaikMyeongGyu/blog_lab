package com.alarm.alarm.member.repository;

import com.alarm.alarm.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
