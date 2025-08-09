package com.dmartinez.api.service.invoice.impl;

import com.dmartinez.api.model.Invoice;
import com.dmartinez.api.model.dto.CreateInvoiceDTO;
import com.dmartinez.api.model.dto.InvoiceResponseDTO;
import com.dmartinez.api.model.mapper.InvoiceMapper;
import com.dmartinez.api.repository.InvoiceRepository;
import com.dmartinez.api.service.invoice.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    private final Path storageFolder = Paths.get("invoices");

    @Override
    public InvoiceResponseDTO createInvoice(CreateInvoiceDTO request) {

        try {
            // 1. Guardar archivo
            MultipartFile file = request.getFileName();
            String storedFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Files.createDirectories(storageFolder);
            Path destination = storageFolder.resolve(storedFileName);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            // 2. Generar URL pública (ajústala si usas dominio real o nginx)
            String fileUrl = "http://localhost:8002/invoices/" + storedFileName;

            // 3. Mapear y guardar
            Invoice invoice = invoiceMapper.fromRequest(request, fileUrl);
            Invoice savedInvoice = invoiceRepository.save(invoice);

            // 4. Convertir a DTO de respuesta
            return invoiceMapper.toDTO(savedInvoice);

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
}
