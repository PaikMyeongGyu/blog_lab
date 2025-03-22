package com.email.email.member.domain.repository;

import com.email.email.member.domain.Email;
import com.email.email.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.status = 'ACTIVE' WHERE m.id = :memberId")
    void activeMemberStatus(@Param("memberId") Long memberId);

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.status = 'DELETE' WHERE m.id = :memberId")
    void deleteMemberStatus(@Param("memberId") Long memberId);

    Optional<Member> findByEmail(Email email);
}
