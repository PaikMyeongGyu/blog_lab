package com.aop.aop_expression.service;

import com.aop.aop_expression.annotation.TeamLeaderV1;
import com.aop.aop_expression.annotation.TeamLeaderV2;
import com.aop.aop_expression.repository.MemberJpaRepository;
import com.aop.aop_expression.repository.TeamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final MemberJpaRepository memberJpaRepository;
    private final TeamJpaRepository teamJpaRepository;

    @TeamLeaderV1
    public void changeTeamInfo(Long userId, Long teamId) {
        // 팀 내용 변경
    }

    @TeamLeaderV1
    public void changeTeamInfoV2(Long teamId, Long userId) {
        // 팀 내용 변경
    }

    @TeamLeaderV2(teamId = "#teamId", userId = "#userId")
    public void changeTeamInfoV3(Long teamId, Long userId) {
        // 팀 내용 변경
    }
}
