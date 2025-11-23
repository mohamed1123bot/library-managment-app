//package com.example.project10;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/invoices")
//public class InvoiceController {
//
//    @Autowired
//    private InvoiceService invoiceService;
//
//    @PostMapping("/{memberId}")
//    public ResponseEntity<Invoice> createInvoice(@PathVariable Long memberId) {
//        return ResponseEntity.ok(invoiceService.createInvoice(memberId));
//    }
//
//    @PostMapping("/generate/{memberId}")
//    public ResponseEntity<Invoice> generateInvoice(@PathVariable Long memberId) {
//        return ResponseEntity.ok(invoiceService.createOrUpdateInvoice(memberId));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
//        return ResponseEntity.ok(invoiceService.updateInvoice(id, invoice));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteInvoice(@PathVariable Long id) {
//        invoiceService.deleteInvoice(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
//        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
//    }
//
//    @GetMapping("/member/{memberId}")
//    public ResponseEntity<List<Invoice>> getInvoicesByMember(@PathVariable Long memberId) {
//        return ResponseEntity.ok(invoiceService.getInvoicesByMember(memberId));
//    }
//
//    @GetMapping("/status")
//    public ResponseEntity<List<Invoice>> getInvoicesByStatus(@RequestParam String status) {
//        return ResponseEntity.ok(invoiceService.getInvoicesByStatus(status));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Invoice>> getAllInvoices() {
//        return ResponseEntity.ok(invoiceService.getAllInvoices());
//    }
//
//    @PutMapping("/pay/{id}")
//    public ResponseEntity<Invoice> payInvoice(@PathVariable Long id) {
//        return ResponseEntity.ok(invoiceService.payInvoice(id));
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//    }
//
//    @ExceptionHandler(IllegalStateException.class)
//    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
//    }
//}
package com.example.project10;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/update/{memberId}")
    public ResponseEntity<InvoiceDTO> createOrUpdateInvoice(@PathVariable Long memberId,
                                                            @RequestBody Loan loan) {
        Invoice invoice = invoiceService.createOrUpdateInvoice(memberId, loan);
        return ResponseEntity.ok(new InvoiceDTO(invoice));
    }

    @PostMapping("/create/{memberId}")
    public ResponseEntity<InvoiceDTO> createInvoice(@PathVariable Long memberId) {
        Invoice invoice = invoiceService.createInvoice(memberId);
        return ResponseEntity.ok(new InvoiceDTO(invoice));
    }

    @PutMapping("/pay/{invoiceId}")
    public ResponseEntity<InvoiceDTO> payInvoice(@PathVariable Long invoiceId) {
        Invoice invoice = invoiceService.payInvoice(invoiceId);
        return ResponseEntity.ok(new InvoiceDTO(invoice));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        List<InvoiceDTO> dtos = invoices.stream().map(InvoiceDTO::new).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(new InvoiceDTO(invoice));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByMember(@PathVariable Long memberId) {
        List<Invoice> invoices = invoiceService.getInvoicesByMember(memberId);
        List<InvoiceDTO> dtos = invoices.stream().map(InvoiceDTO::new).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/status")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByStatus(@RequestParam String status) {
        List<Invoice> invoices = invoiceService.getInvoicesByStatus(status);
        List<InvoiceDTO> dtos = invoices.stream().map(InvoiceDTO::new).toList();
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok().build();
    }
}
