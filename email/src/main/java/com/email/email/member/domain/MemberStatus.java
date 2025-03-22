package com.email.email.member.domain;

/**
 * BLOCKED : 회원이 탈퇴가 된 경우
 * INACTIVE : 회원이 회원가입을 했으나, 이메일 인증이 되지 않은 경우
 * ACTIVE : 회원이 회원가입을 하고, 이메일 인증을 한 경우
 */
public enum MemberStatus {
    ACTIVE, INACTIVE, BLOCKED
}
