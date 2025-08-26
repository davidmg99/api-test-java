package com.dmartinez.api.service.invoice.impl;

import com.dmartinez.api.model.dto.CreateInvoiceDTO;
import com.dmartinez.api.model.dto.InvoiceResponseDTO;
import com.dmartinez.api.model.mapper.InvoiceMapper;
import com.dmartinez.api.model.Invoice;
import com.dmartinez.api.repository.InvoiceRepository;
import com.dmartinez.api.service.file.FileStorageService;

import com.dmartinez.api.service.urlgenerator.FileUrlGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private InvoiceMapper invoiceMapper;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private FileUrlGenerator fileUrlGenerator;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test 1: Flujo correcto de createInvoice()
    @Test
    void createInvoice_ShouldReturnResponseDTO_WhenSuccessful() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "invoice.pdf", "application/pdf", "data".getBytes());
        CreateInvoiceDTO request = new CreateInvoiceDTO();
        request.setFileName(file);

        String storedFileName = UUID.randomUUID() + "_invoice.pdf";
        String fileUrl = "http://localhost/invoices/" + storedFileName;

        Invoice invoice = new Invoice();
        Invoice savedInvoice = new Invoice();
        InvoiceResponseDTO responseDTO = new InvoiceResponseDTO();

        when(fileStorageService.storeFile(file)).thenReturn(storedFileName);
        when(fileUrlGenerator.generateUrl(storedFileName)).thenReturn(fileUrl);
        when(invoiceMapper.fromRequest(request, fileUrl)).thenReturn(invoice);
        when(invoiceRepository.save(invoice)).thenReturn(savedInvoice);
        when(invoiceMapper.toDTO(savedInvoice)).thenReturn(responseDTO);

        // Act
        InvoiceResponseDTO result = invoiceService.createInvoice(request);

        // Assert
        assertEquals(responseDTO, result);
        verify(fileStorageService).storeFile(file);
        verify(fileUrlGenerator).generateUrl(storedFileName);
        verify(invoiceMapper).fromRequest(request, fileUrl);
        verify(invoiceRepository).save(invoice);
        verify(invoiceMapper).toDTO(savedInvoice);
    }


    // Test 2: Lanza excepción si ocurre IOException
    @Test
    void createInvoice_ShouldThrowRuntimeException_WhenIOExceptionOccurs() throws IOException {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "invoice.pdf", "application/pdf", "data".getBytes());
        CreateInvoiceDTO request = new CreateInvoiceDTO();
        request.setFileName(file);

        when(fileStorageService.storeFile(file)).thenThrow(new IOException("Simulated error"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            invoiceService.createInvoice(request);
        });

        assertEquals("Error al guardar el archivo", thrown.getMessage());
        verify(fileStorageService).storeFile(file);
        verifyNoMoreInteractions(invoiceMapper, invoiceRepository);
    }

    // Test 3: Lanza excepción si el archivo está vacío
    @Test
    void createInvoice_ShouldThrowException_WhenFileIsEmpty() {
        // Arrange
        MockMultipartFile emptyFile = new MockMultipartFile("file", "empty.pdf", "application/pdf", new byte[0]);
        CreateInvoiceDTO request = new CreateInvoiceDTO();
        request.setFileName(emptyFile);

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            invoiceService.createInvoice(request);
        });

        assertEquals("El archivo está vacío", thrown.getMessage());
        verifyNoInteractions(invoiceRepository, invoiceMapper, fileStorageService);
    }

    // Test 4: Flujo correcto de getAllInvoices()
    @Test
    void getAllInvoices_ShouldReturnMappedDTOs() {
        // Arrange
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        List<Invoice> invoices = List.of(invoice1, invoice2);

        InvoiceResponseDTO dto1 = new InvoiceResponseDTO();
        InvoiceResponseDTO dto2 = new InvoiceResponseDTO();

        when(invoiceRepository.findAll()).thenReturn(invoices);
        when(invoiceMapper.toDTO(invoice1)).thenReturn(dto1);
        when(invoiceMapper.toDTO(invoice2)).thenReturn(dto2);

        // Act
        List<InvoiceResponseDTO> result = invoiceService.getAllInvoices();

        // Assert
        assertEquals(invoices.size(), result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
        verify(invoiceRepository).findAll();
        verify(invoiceMapper, times(invoices.size())).toDTO(any(Invoice.class));
    }

    // Test 5: getAllInvoices devuelve lista vacía correctamente
    @Test
    void getAllInvoices_ShouldReturnEmptyList_WhenNoInvoicesExist() {
        // Arrange
        when(invoiceRepository.findAll()).thenReturn(List.of());

        // Act
        List<InvoiceResponseDTO> result = invoiceService.getAllInvoices();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(invoiceRepository).findAll();
        verifyNoInteractions(invoiceMapper);
    }
}
