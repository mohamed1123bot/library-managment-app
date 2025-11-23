package com.example.project10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/borrow")
    public ResponseEntity<LoanResponseDTO> borrowBook(@RequestBody Loan loan) {
        return ResponseEntity.ok(loanService.borrowBook(loan));
    }

    @PostMapping("/purchase")
    public ResponseEntity<LoanResponseDTO> purchaseBook(@RequestBody Loan loan) {
        return ResponseEntity.ok(loanService.purchaseBook(loan));
    }

    @PutMapping("/return/{loanId}")
    public ResponseEntity<LoanResponseDTO> returnBook(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.returnBook(loanId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<LoanResponseDTO>> getActiveLoans() {
        return ResponseEntity.ok(
                loanService.getActiveLoans().stream()
                        .map(LoanResponseDTO::new)
                        .toList()
        );
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<LoanResponseDTO>> getLoansByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(
                loanService.getLoansByMember(memberId).stream()
                        .map(LoanResponseDTO::new)
                        .toList()
        );
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<LoanResponseDTO>> getLoansByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(
                loanService.getLoansByBook(bookId).stream()
                        .map(LoanResponseDTO::new)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable Long id) {
        return ResponseEntity.ok(new LoanResponseDTO(loanService.getLoanById(id)));
    }

    @GetMapping
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        return ResponseEntity.ok(
                loanService.getAllLoans().stream()
                        .map(LoanResponseDTO::new)
                        .toList()
        );
    }
}
