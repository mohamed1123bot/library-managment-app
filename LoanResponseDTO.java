package com.example.project10;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
public class LoanResponseDTO {
    private Long loanId;
    private String bookTitle;
    private String memberName;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private String status;
    private boolean purchased;
    private double fine;
    private double finalPrice;
    private double basePrice;
    private double purchasePrice;
    private InvoiceDTO invoice;

    public LoanResponseDTO(Loan loan) {
        this.loanId = loan.getId();
        this.bookTitle = loan.getBook().getTitle();
        this.memberName = loan.getMember().getName();
        this.loanDate = loan.getLoanDate();
        this.dueDate = loan.getDueDate();
        this.status = loan.getStatus();
        this.purchased = loan.isPurchased();
        this.fine = loan.getFine();
        this.finalPrice = loan.getFinalPrice();
        this.basePrice = loan.getBook().getPrice();
        this.purchasePrice = loan.getBook().getPurchasePrice();


        this.invoice = loan.getInvoice() != null ? new InvoiceDTO(loan.getInvoice()) : null;
    }
}

