package com.aop.aop_expression.repository;

import com.aop.aop_expression.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {
}
