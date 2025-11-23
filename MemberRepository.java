package com.example.project10;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.loans")
    List<Member> fetchAllWithLoans();

    List<Member> findByNameContainingIgnoreCase(String keyword);
    Optional<Member> findByEmailAndPhoneAndPassword(String email, String phone, String password);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    }

