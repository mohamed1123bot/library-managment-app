package com.example.project10;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByBookIdAndStatus(Long bookId, String status);

    List<Loan> findByMemberId(Long memberId);
    List<Loan> findByStatus(String status);
    List<Loan> findByBookId(Long bookId);
}