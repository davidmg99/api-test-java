package com.dmartinez.api.controller;


import com.dmartinez.api.model.dto.CreateInvoiceDTO;
import com.dmartinez.api.model.dto.InvoiceResponseDTO;
import com.dmartinez.api.service.invoice.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InvoiceResponseDTO> createInvoice(
            @Valid @ModelAttribute CreateInvoiceDTO dto) {

        InvoiceResponseDTO createdInvoice = invoiceService.createInvoice(dto);
        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoices() {
        List<InvoiceResponseDTO> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok(invoices);
    }

}
