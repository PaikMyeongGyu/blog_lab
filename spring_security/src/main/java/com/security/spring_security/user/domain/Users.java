package com.security.spring_security.user.domain;

import static com.security.spring_security.user.domain.UserRole.USER;
import static com.security.spring_security.user.domain.UserStatus.ENABLED;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Users")
public class Users {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(STRING)
    @Column(nullable = false)
    private UserStatus status;

    @CreatedDate
    @Column(name = "join_date", updatable = false)
    private LocalDateTime joinDate;

    @Builder
    private Users(
            final String username,
            final String email,
            final String password,
            final UserRole role,
            final UserStatus status
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    private Users(final Long userId, final UserRole role) {
        this.id = userId;
        this.role = role;
    }

    public static Users createNewUser(
            final String username,
            final String email,
            final String password
    ) {
        return new UsersBuilder()
                .username(username)
                .email(email)
                .password(password)
                .role(USER)
                .status(ENABLED)
                .build();
    }

    public static Users simpleUser(
            final Long userId,
            final UserRole role
    ) {
        return new Users(userId, role);
    }

}
