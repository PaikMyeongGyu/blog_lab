package com.security.spring_security.user.domain.repository;

import com.security.spring_security.user.domain.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<Users, Long> {
    Optional<Users> findUserByEmail(String email);
    Optional<Users> findUserByUsername(String username);

    @Modifying
    @Transactional
    @Query("""
        UPDATE Users user
        SET user.status = "INACTIVE"
        WHERE user.email = :email
    """)
    void inactiveUserByEmail(@Param("email") String email);
}
