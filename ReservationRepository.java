package com.example.project10;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberId(Long memberId);
    List<Reservation> findByBookId(Long bookId);
    List<Reservation> findByStatus(String status);

        List<Reservation> findByBookIdAndStatusOrderByReservationDateAsc(Long bookId, String status);


}