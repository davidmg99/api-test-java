package com.dmartinez.api.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InvoiceResponseDTO {
    private Long uuid;
    private String category;
    private LocalDateTime startPeriod;
    private LocalDateTime endPeriod;
    private String fileUrl;
}
