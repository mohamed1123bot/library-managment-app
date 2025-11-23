package com.example.project10;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByMemberId(Long memberId);
    List<Invoice> findByStatus(String status);
    Optional<Invoice> findFirstByMemberIdAndStatus(Long memberId, String status);
        List<Invoice> findByPaidFalse();
    Optional<Invoice> findById(Long id);





}