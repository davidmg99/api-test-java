package com.dmartinez.api.service.invoice;

import com.dmartinez.api.model.dto.CreateInvoiceDTO;
import com.dmartinez.api.model.dto.InvoiceResponseDTO;

import java.util.List;

public interface InvoiceService {
    InvoiceResponseDTO createInvoice(CreateInvoiceDTO request);

    List<InvoiceResponseDTO> getAllInvoices();
}
