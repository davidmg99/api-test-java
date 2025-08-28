package com.dmartinez.api.service.invoice.impl;

import com.dmartinez.api.model.Invoice;
import com.dmartinez.api.model.dto.CreateInvoiceDTO;
import com.dmartinez.api.model.dto.InvoiceResponseDTO;
import com.dmartinez.api.model.mapper.InvoiceMapper;
import com.dmartinez.api.repository.InvoiceRepository;
import com.dmartinez.api.service.file.FileStorageService;
import com.dmartinez.api.service.invoice.InvoiceService;
import com.dmartinez.api.service.urlgenerator.FileUrlGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final FileStorageService fileStorageService;
    private final FileUrlGenerator fileUrlGenerator;

    @Override
    public InvoiceResponseDTO createInvoice(CreateInvoiceDTO request) {
        MultipartFile file = request.getFile();

        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        try {
            String storedFileName = fileStorageService.storeFile(file);
            String fileUrl = fileUrlGenerator.generateUrl(storedFileName);
            Invoice invoice = invoiceMapper.fromRequest(request, fileUrl);
            Invoice saved = invoiceRepository.save(invoice);
            return invoiceMapper.toDTO(saved);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo", e);
        }
    }


    @Override
    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    private Invoice mapToEntity(CreateInvoiceDTO request, String fileUrl) {
        return invoiceMapper.fromRequest(request, fileUrl);
    }

    private Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    private InvoiceResponseDTO mapToResponse(Invoice invoice) {
        return invoiceMapper.toDTO(invoice);
    }
}
