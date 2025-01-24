package com.oauth2.login.user.service;

import com.oauth2.login.auth.domain.UserTokens;
import com.oauth2.login.common.exception.ExceptionCode;
import com.oauth2.login.common.exception.SocialLoginException;
import com.oauth2.login.user.domain.User;
import com.oauth2.login.user.domain.request.UserUpdateRequest;
import com.oauth2.login.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oauth2.login.common.exception.ExceptionCode.INVALID_REQUEST;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;

    @Transactional(readOnly = true)
    public User findUserBySocialId(String socialId) {
        return userJpaRepository
                .findBySocialLoginId(socialId)
                .orElseThrow(() -> new SocialLoginException(INVALID_REQUEST));
    }

    @Transactional
    public void modifyUser(User user, UserUpdateRequest updateRequest) {
        user.update(
                updateRequest.name(),
                updateRequest.nickname(),
                updateRequest.profileUri()
        );
        userJpaRepository.save(user);
    }
}
