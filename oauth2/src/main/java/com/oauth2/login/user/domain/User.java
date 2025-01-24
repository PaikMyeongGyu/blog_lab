package com.oauth2.login.user.domain;

import com.oauth2.login.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.oauth2.login.user.domain.UserStatus.ACTIVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Table(name =  "USERS")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_social_login_id", nullable = false, unique = true)
    private String socialLoginId;

    @Column(name = "user_email", length = 40, unique = true)
    private String email;

    @Column(name = "user_name", nullable = false, length = 10)
    private String name;

    @Column(name = "user_nickname", nullable = false, length = 20, unique = true)
    private String nickname;

    @Column(name = "user_profile_uri", nullable = false)
    private String profileUri;

    @Enumerated(STRING)
    @Column(name = "user_status")
    private UserStatus status;

    @Builder
    protected User(
            String socialLoginId,
            String email,
            String name,
            String nickname,
            String profileUri
    ) {
        this.socialLoginId = socialLoginId;
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.profileUri = profileUri;
        this.status = ACTIVE;
    }

    public User createNewSocialUser(
            String socialLoginId,
            String email,
            String name,
            String nickname,
            String profileUri
    ) {
        return new User(socialLoginId, email, name, nickname, profileUri);
    }

    public void update(String name, String nickname, String profileUri) {
        this.name = name;
        this.nickname = nickname;
        this.profileUri = profileUri;
    }
}
