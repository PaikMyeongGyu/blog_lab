package com.aop.aop_expression.aop;

import com.aop.aop_expression.annotation.TeamLeaderV2;
import com.aop.aop_expression.domain.Team;
import com.aop.aop_expression.repository.MemberJpaRepository;
import com.aop.aop_expression.repository.TeamJpaRepository;
import com.bisang.redlock.common.util.CSpringExpressionParser;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class TeamRoleAspect {
    private final TeamJpaRepository teamJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    @Around("@annotation(com.aop.aop_expression.annotation.TeamLeaderV1) && args(userId, teamId, ..)")
    public Object validateTeamLeaderV1(ProceedingJoinPoint joinPoint, Long userId, Long teamId) throws Throwable {
        Team team = teamJpaRepository.findById(teamId)
                                     .orElseThrow(() -> new IllegalArgumentException("Team not found"));

        if (!team.getLeaderId().equals(userId)) {
            throw new IllegalArgumentException("Invalid user id");
        }

        return joinPoint.proceed();
    }

    @Around("@annotation(teamLeaderV2)")
    public Object validateTeamLeaderV2(ProceedingJoinPoint joinPoint, TeamLeaderV2 teamLeaderV2) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        Object teamIdValue = CSpringExpressionParser.getDynamicValue(parameterNames, args, teamLeaderV2.teamId());
        Object userIdValue = CSpringExpressionParser.getDynamicValue(parameterNames, args, teamLeaderV2.userId());

        if (!(teamIdValue instanceof Long) || !(userIdValue instanceof Long)) {
            throw new IllegalArgumentException("teamId와 userId는 Long 타입이어야 합니다.");
        }

        Long teamId = (Long) teamIdValue;
        Long userId = (Long) userIdValue;

        Team team = teamJpaRepository.findById(teamId)
            .orElseThrow(() -> new IllegalArgumentException("Team not found"));

        if (!team.getLeaderId().equals(userId)) {
            throw new IllegalArgumentException("Invalid user id");
        }

        return joinPoint.proceed();
    }
}
