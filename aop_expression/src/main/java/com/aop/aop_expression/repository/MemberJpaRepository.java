package com.aop.aop_expression.repository;

import com.aop.aop_expression.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
}
