package com.dmartinez.api.model.mapper;

import com.dmartinez.api.model.Invoice;
import com.dmartinez.api.model.dto.CreateInvoiceDTO;
import com.dmartinez.api.model.dto.InvoiceResponseDTO;

public class InvoiceMapper {

    // De DTO de creación a entidad
    public Invoice fromRequest(CreateInvoiceDTO request, String fileUrl) {
        Invoice invoice = new Invoice();
        invoice.setCategory(request.getCategory());
        invoice.setStartPeriod(request.getStartPeriod());
        invoice.setEndPeriod(request.getEndPeriod());
        invoice.setFileUrl(fileUrl);
        return invoice;
    }

    // De entidad a DTO de respuesta
    public InvoiceResponseDTO toDTO(Invoice invoice) {
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setUuid(invoice.getInvoiceId());
        dto.setCategory(invoice.getCategory());
        dto.setStartPeriod(invoice.getStartPeriod());
        dto.setEndPeriod(invoice.getEndPeriod());
        dto.setFileUrl(invoice.getFileUrl());
        return dto;
    }
}
