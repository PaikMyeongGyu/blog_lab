package com.email.email.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.email.email.member.domain.MemberStatus.INACTIVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(
        name = "mmeber",
        indexes = {
                @Index(name = "idx_member_status_id_desc", columnList = "status, member_id desc")
        }
)
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    @Column(name = "email", unique = true, length = 64)
    private Email email;

    @Column(name = "name", unique = true, length = 64)
    private String name;

    @Column(name = "password", length = 32)
    private String password;

    @Enumerated(STRING)
    @Column(name = "status")
    private MemberStatus status;

    public Member(Email email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.status = INACTIVE;
    }
}
