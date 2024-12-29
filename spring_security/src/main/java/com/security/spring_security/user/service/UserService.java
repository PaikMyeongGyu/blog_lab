package com.security.spring_security.user.service;

import com.security.spring_security.global.exception.UserException;
import com.security.spring_security.user.domain.Users;
import com.security.spring_security.user.domain.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.security.spring_security.global.exception.exceptioncode.ExceptionCode.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public void joinUser(String username, String email, String password) {
        isDuplicateUser(username, email);

        // passwordValidation 추후 적용 필요
        // nickNameValidation 추후 적용 필요
        passwordValidation(password);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userJpaRepository.save(Users.createNewUser(username, email, hashedPassword));
    }

    @Transactional
    public void inactivateUser(String email, String password) {
        Users findUser = getUserBy(email);
        passwordMatchValidation(findUser.getPassword(), password);
        userJpaRepository.inactiveUserByEmail(email);
    }

    @Transactional(readOnly = true)
    public Users findUserByEmailAndPassword(String email, String password) {
        Users findUser = getUserBy(email);
        passwordMatchValidation(findUser.getPassword(), password);
        return findUser;
    }

    @Transactional(readOnly = true)
    public Optional<Users> findUserById(Long userId) {
        return userJpaRepository.findById(userId);
    }

    private void passwordMatchValidation(String userPassword, String password) {
        if (!BCrypt.checkpw(password, userPassword)) {
            throw new UserException(INVALID_REQUEST);
        }
    }

    private Users getUserBy(String email) {
        return userJpaRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND_WITH_EMAIL));
    }

    private void isDuplicateUser(String username, String email) {
        userJpaRepository.findUserByEmail(email)
                .ifPresent(user -> { throw new UserException(USER_DUPLICATE_WITH_EMAIL); });
        userJpaRepository.findUserByUsername(username)
                .ifPresent(user -> { throw new UserException(USER_DUPLICATE_WITH_USERNAME); });
    }

    private void passwordValidation(String password) {
        if (!isValidPassword(password)) {
            throw new UserException(PASSWORD_VALIDATION);
        }
    }

    /**
     * 닉네임 유효성 검증 메서드
     *
     * 유효한 닉네임은 다음 조건을 충족해야 한다.
     * 1. 닉네임이 비어있지 않아야 한다.(null이나 공백이 아니다.)
     * 2. 공백을 포함되는 걸 허용해선 안된다.(사칭 등의 문제가 발생할 수 있다.)
     * 3. 영어 소대문자, 숫자만 허용을 한다.
     * 4. 길이는 최소 6, 최대 20으로 허용한다.
     *
     * @param nickname 입력 받은 닉네임
     * @return 닉네임이 유효하다면 true, 아니라면 false
     */
    public static boolean isValidNickname(String nickname) {
        return nickname != null &&
                !nickname.isBlank() &&
                nickname.matches("^[a-zA-Z0-9]+$") &&
                6 <= nickname.length() && nickname.length() <= 20;
    }

    /**
     * 패스워드 유효성 검증 메서드
     *
     * 유효한 패스워드는 다음 조건을 충족해야 한다.
     * 1. 비어있지 않음(null 또는 공백이 아니다.)
     * 2. 길이가 8자 이상 15자 이하
     * 3. 최소 1개의 숫자, 영문자, 특수 문자(!@#$%^&*()_+=)를 포함해야 한다.
     *
     * @param password 입력 받은 패스워드
     * @return 패스워드가 유효하다면 true, 아니라면 false
     */
    private boolean isValidPassword(String password) {
        return !StringUtils.isBlank(password) &&
                password.length() >= 8 && password.length() <= 15 &&
                password.matches(".*\\d.*") &&
                password.matches(".*[a-zA-Z].*") &&
                password.matches(".*[!@#$%^&*()\\-+=].*");
    }
}
