package com.oauth2.login.user.repository;

import com.oauth2.login.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
}
