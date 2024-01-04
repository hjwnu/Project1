package com.project1.domain.member.repository;

import com.project1.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByName(String name);
}
