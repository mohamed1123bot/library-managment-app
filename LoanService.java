package com.example.project10;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;
    private final InvoiceService invoiceService;
    private LoanResponseDTO loanResponseDTO;

    public LoanService(LoanRepository loanRepository,
                       MemberRepository memberRepository,
                       BookRepository bookRepository,
                       ReservationRepository reservationRepository,
                       InvoiceService invoiceService) {
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.reservationRepository = reservationRepository;
        this.invoiceService = invoiceService;

    }

    public LoanResponseDTO borrowBook(Loan loan) {
        Member member = memberRepository.findById(loan.getMember().getId()).orElseThrow(() -> new RuntimeException("Member not found"));
        Book book = bookRepository.findById(loan.getBook().getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getTotalCopies() > 0) {
            book.setTotalCopies(book.getTotalCopies() - 1);
            book.setLoanCount(book.getLoanCount() + 1);
            bookRepository.save(book);

            loan.setMember(member);
            loan.setBook(book);
            loan.setLoanDate(LocalDate.now());
            loan.setDueDate(LocalDate.now().plusDays(14));
            loan.setStatus("BORROWED");
            loan.setPurchased(false);
            loan.setFine(0.0);
            loan.setFinalPrice(book.getPrice());

            Loan savedLoan = loanRepository.save(loan);
            invoiceService.createOrUpdateInvoice(member.getId(), savedLoan);

            return new LoanResponseDTO(savedLoan);
        } else {
            Reservation reservation = new Reservation();
            reservation.setBook(book);
            reservation.setMember(member);
            reservation.setReservationDate(LocalDate.now());
            reservation.setStatus("WAITING");
            reservationRepository.save(reservation);



            throw new RuntimeException("No copies available. Reservation created for this member.");
        }
    }

    @Transactional
    public LoanResponseDTO purchaseBook(Loan loan) {
        Member member = memberRepository.findById(loan.getMember().getId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Book book = bookRepository.findById(loan.getBook().getId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getTotalCopies() < 5) {
            throw new RuntimeException("Not enough copies to allow purchase.");
        }

        book.setTotalCopies(book.getTotalCopies() - 1);
        bookRepository.save(book);

        loan.setMember(member);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(null);
        loan.setStatus("PURCHASED");
        loan.setPurchased(true);
        loan.setFine(0.0);


        double purchasePrice = book.getPurchasePrice() > 0 ? book.getPurchasePrice() : book.getPrice() * 2;
        loan.setFinalPrice(purchasePrice);

        Loan savedLoan = loanRepository.save(loan);
        invoiceService.createOrUpdateInvoice(member.getId(), savedLoan);

        return new LoanResponseDTO(savedLoan);
    }


    @Transactional
    public LoanResponseDTO returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.isPurchased()) {
            throw new RuntimeException("This book was purchased. Cannot be returned.");
        }

        if ("RETURNED".equalsIgnoreCase(loan.getStatus())) {
            throw new RuntimeException("This loan has already been returned.");
        }

        loan.setReturnDate(LocalDate.now());
        loan.setStatus("RETURNED");

        if (loan.getDueDate() != null && loan.getReturnDate().isAfter(loan.getDueDate())) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(
                    loan.getDueDate(),
                    loan.getReturnDate()
            );
            double fineAmount = daysLate * 10.0;
            loan.setFine(fineAmount);
        } else {
            loan.setFine(0.0);
        }

        Book book = loan.getBook();

        List<Reservation> reservations = reservationRepository
                .findByBookIdAndStatusOrderByReservationDateAsc(book.getId(), "WAITING");

        if (!reservations.isEmpty()) {
            Reservation firstReservation = reservations.get(0);
            firstReservation.setStatus("FULFILLED");
            reservationRepository.save(firstReservation);

            Loan newLoan = new Loan();
            newLoan.setBook(book);
            newLoan.setMember(firstReservation.getMember());
            newLoan.setLoanDate(LocalDate.now());
            newLoan.setDueDate(LocalDate.now().plusDays(14));
            newLoan.setStatus("BORROWED");
            newLoan.setFine(0.0);
            newLoan.setPurchased(false);
            newLoan.setFinalPrice(book.getPrice());

            book.setLoanCount(book.getLoanCount() + 1);
            bookRepository.save(book);

            loanRepository.save(loan);
            Loan savedNewLoan = loanRepository.save(newLoan);
            invoiceService.createOrUpdateInvoice(firstReservation.getMember().getId(), savedNewLoan);

            return new LoanResponseDTO(savedNewLoan);
        } else {
            book.setTotalCopies(book.getTotalCopies() + 1);
            bookRepository.save(book);
        }

        Loan savedLoan = loanRepository.save(loan);
        return new LoanResponseDTO(savedLoan);
    }

    public List<Loan> getActiveLoans() {
        return loanRepository.findByStatus("BORROWED");
    }

    public List<Loan> getLoansByMember(Long memberId) {
        return loanRepository.findByMemberId(memberId);
    }

    public List<Loan> getLoansByBook(Long bookId) {
        return loanRepository.findByBookId(bookId);
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
}
