package com.aop.aop_expression.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(
    name = "team"
)
public class Team {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name=  "team_id")
    private Integer teamId;

    @Column(name = "leader_id", nullable = false)
    private Integer leaderId;

    @Column(name = "team_name", nullable = false, length = 20)
    private String teamName;

    public Team(Integer leaderId, String teamName) {
        this.leaderId = leaderId;
        this.teamName = teamName;
    }
}
