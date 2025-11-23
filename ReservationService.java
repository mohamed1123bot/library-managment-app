package com.example.project10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;



    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    public Reservation reserveBook(Reservation reservation) {
        reservation.setReservationDate(LocalDate.now());
        reservation.setStatus("ACTIVE");

        Long memberId = reservation.getMember().getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        reservation.setMember(member);

        Long bookId = reservation.getBook().getId();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        reservation.setBook(book);

        return reservationRepository.save(reservation);
    }





    public Reservation cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        reservation.setStatus("CANCELLED");
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByMember(Long memberId) {
        return reservationRepository.findByMemberId(memberId);
    }

    public List<Reservation> getReservationsByBook(Long bookId) {
        return reservationRepository.findByBookId(bookId);
    }

    public List<Reservation> getReservationsByStatus(String status) {
        return reservationRepository.findByStatus(status);
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
  }
}