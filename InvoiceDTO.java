package com.example.project10;

import com.example.project10.Invoice;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Setter
@Getter
public class InvoiceDTO {
    private Long id;
    private BigDecimal amount;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String status;
    private String memberName;
    private Long memberId;

    public InvoiceDTO(Invoice invoice) {
        this.id = invoice.getId();
        this.amount = invoice.getAmount();
        this.issueDate = invoice.getIssueDate();
        this.dueDate = invoice.getDueDate();
        this.status = invoice.getStatus();
        this.memberName = invoice.getMember() != null ? invoice.getMember().getName() : null;
        this.memberId = invoice.getMember() != null ? invoice.getMember().getId() : null;
    }

    // Getters & Setters
}



