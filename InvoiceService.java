//package com.example.project10;
//
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class InvoiceService {
//
//    @Autowired
//    private InvoiceRepository invoiceRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private LoanRepository loanRepository;
//
//
//
//public Invoice createInvoice(Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//
//        double total = 0.0;
//
//        List<Loan> loans = loanRepository.findByMemberId(memberId);
//        for (Loan loan : loans) {
//            if (loan.getBook() != null && loan.getBook().getPrice() != null) {
//                total += loan.getBook().getPrice(); // سعر الكتاب
//            }
//            total += loan.getFine();
//        }
//
//        Invoice invoice = new Invoice();
//        invoice.setMember(member);
//        invoice.setIssueDate(LocalDate.now());
//        invoice.setDueDate(LocalDate.now().plusDays(7));
//        invoice.setStatus("UNPAID");
//        invoice.setAmount(BigDecimal.valueOf(total));
//
//        return invoiceRepository.save(invoice);
//    }
//
//    public Invoice updateInvoice(Long id, Invoice updatedInvoice) {
//        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
//        invoice.setDueDate(updatedInvoice.getDueDate());
//        invoice.setStatus(updatedInvoice.getStatus());
//        return invoiceRepository.save(invoice);
//    }
//
//    public void deleteInvoice(Long id) {
//        invoiceRepository.deleteById(id);
//    }
//
//    public Invoice getInvoiceById(Long id) {
//        return invoiceRepository.findById(id).orElse(null);
//    }
//
//    public List<Invoice> getInvoicesByMember(Long memberId) {
//        return invoiceRepository.findByMemberId(memberId);
//    }
//
//    public List<Invoice> getInvoicesByStatus(String status) {
//        return invoiceRepository.findByStatus(status);
//    }
//
//    public List<Invoice> getAllInvoices() {
//        return invoiceRepository.findAll();
//    }
//
//    public Invoice payInvoice(Long invoiceId) {
//        Invoice invoice = invoiceRepository.findById(invoiceId)
//                .orElseThrow(() -> new RuntimeException("Invoice not found"));
//
//        if ("PAID".equalsIgnoreCase(invoice.getStatus())) {
//            throw new IllegalStateException("Invoice is already paid");
//        }
//
//        invoice.setStatus("PAID");
//        invoice.setDueDate(LocalDate.now());
//        return invoiceRepository.save(invoice);
//    }
//
//    @Transactional
//
//    public Invoice createOrUpdateInvoice(Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//
//        Invoice invoice = invoiceRepository.findFirstByMemberIdAndStatus(memberId, "UNPAID")
//                .orElse(null);
//
//        if (invoice == null) {
//            invoice = new Invoice();
//            invoice.setMember(member);
//            invoice.setIssueDate(LocalDate.now());
//            invoice.setDueDate(LocalDate.now().plusDays(7));
//            invoice.setStatus("UNPAID");
//            invoice.setPaid(false);
//            invoice.setLoans(new ArrayList<>());
//        }
//
//        List<Loan> newLoans = loanRepository.findByMemberId(memberId).stream()
//                .filter(l -> "BORROWED".equalsIgnoreCase(l.getStatus()) && l.getInvoice() == null)
//                .toList();
//
//        for (Loan loan : newLoans) {
//            loan.setInvoice(invoice);
//            invoice.getLoans().add(loan);
//        }
//
//        double total = invoice.getLoans().stream()
//                .mapToDouble(l -> {
//                    double price = (l.getBook() != null && l.getBook().getPrice() != null)
//                            ? l.getBook().getPrice()
//                            : 0.0;
//                    return price + l.getFine();
//                })
//                .sum();
//
//        invoice.setAmount(BigDecimal.valueOf(total));
//
//        invoiceRepository.save(invoice);
//        loanRepository.saveAll(newLoans);
//
//        Invoice savedInvoice = invoiceRepository.save(invoice);
//        System.out.println("Saved invoice ID: " + savedInvoice.getId());
//
//        return invoice;
//
//    }
//
//    }
package com.example.project10;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LoanRepository loanRepository;


    @Transactional
    public Invoice createOrUpdateInvoice(Long memberId, Loan loan) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Invoice invoice = invoiceRepository.findFirstByMemberIdAndStatus(memberId, "UNPAID")
                .orElseGet(() -> {
                    Invoice newInvoice = new Invoice();
                    newInvoice.setMember(member);
                    newInvoice.setIssueDate(LocalDate.now());
                    newInvoice.setDueDate(LocalDate.now().plusDays(7));
                    newInvoice.setStatus("UNPAID");
                    newInvoice.setPaid(false);
                    newInvoice.setLoans(new ArrayList<>());
                    return invoiceRepository.save(newInvoice);
                });

        loan.setInvoice(invoice);
        invoice.getLoans().add(loan);

        double total = invoice.getLoans().stream()
                .mapToDouble(l -> l.getFinalPrice() + l.getFine())
                .sum();

        invoice.setAmount(BigDecimal.valueOf(total));

        loanRepository.save(loan);
        Invoice savedInvoice = invoiceRepository.save(invoice);

        System.out.println("✅ Invoice updated/created with ID: " + savedInvoice.getId());
        return savedInvoice;
    }



    public Invoice createInvoice(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        List<Loan> loans = loanRepository.findByMemberId(memberId);

        double total = loans.stream()
                .mapToDouble(l -> l.getFinalPrice() + l.getFine())
                .sum();

        Invoice invoice = new Invoice();
        invoice.setMember(member);
        invoice.setIssueDate(LocalDate.now());
        invoice.setDueDate(LocalDate.now().plusDays(7));
        invoice.setStatus("UNPAID");
        invoice.setAmount(BigDecimal.valueOf(total));
        invoice.setLoans(new ArrayList<>(loans));

        loans.forEach(l -> l.setInvoice(invoice));
        loanRepository.saveAll(loans);

        System.out.println("📄 Invoice contains " + invoice.getLoans().size() + " loans. Total: " + invoice.getAmount());

        return invoiceRepository.save(invoice);
    }


    public Invoice payInvoice(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if ("PAID".equalsIgnoreCase(invoice.getStatus())) {
            throw new IllegalStateException("Invoice is already paid");
        }

        invoice.setStatus("PAID");
        invoice.setDueDate(LocalDate.now());
        invoice.setPaid(true);
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public List<Invoice> getInvoicesByMember(Long memberId) {
        return invoiceRepository.findByMemberId(memberId);
    }

    public List<Invoice> getInvoicesByStatus(String status) {
        return invoiceRepository.findByStatus(status);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
