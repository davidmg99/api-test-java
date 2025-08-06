package com.dmartinez.api.model.mapper;

import com.dmartinez.api.model.Invoice;
import com.dmartinez.api.model.dto.CreateInvoiceDTO;

public class InvoiceMapper {

    public static CreateInvoiceDTO toDTO(Invoice invoice) {
        CreateInvoiceDTO createInvoiceDTO = new CreateInvoiceDTO();

        createInvoiceDTO.setCategory(invoice.getCategory());
        createInvoiceDTO.setStartPeriod(invoice.getStartPeriod());
        createInvoiceDTO.setEndPeriod(invoice.getEndPeriod());
        createInvoiceDTO.setFileName(createInvoiceDTO.getFileName());

        return createInvoiceDTO;
    }

    public static Invoice toEntity(CreateInvoiceDTO createInvoiceDTO, String fileUrl) {
        Invoice invoice = new Invoice();

        invoice.setCategory(createInvoiceDTO.getCategory());
        invoice.setStartPeriod(createInvoiceDTO.getStartPeriod());
        invoice.setEndPeriod(createInvoiceDTO.getEndPeriod());
        invoice.setFileUrl(fileUrl);

        return invoice;
    }
}
