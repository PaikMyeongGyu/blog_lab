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
    name = "member"
)
public class Member {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private String name;

    public Member(String name) {
        this.name = name;
    }
}
