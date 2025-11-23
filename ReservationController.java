package com.example.project10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> reserveBook(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationService.reserveBook(reservation));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Reservation> cancelReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Reservation>> getReservationsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(reservationService.getReservationsByMember(memberId));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Reservation>> getReservationsByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(reservationService.getReservationsByBook(bookId));
    }

    @GetMapping("/status")
    public ResponseEntity<List<Reservation>> getReservationsByStatus(@RequestParam String status) {
        return ResponseEntity.ok(reservationService.getReservationsByStatus(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok().build();
    }
}