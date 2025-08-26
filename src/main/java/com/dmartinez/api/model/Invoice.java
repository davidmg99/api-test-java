package com.dmartinez.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "invoice_id")
    private Long invoiceId;

    private String category;
    @Column(name = "start_period")
    private LocalDateTime startPeriod;
    @Column(name = "end_period")
    private LocalDateTime endPeriod;
    @Column(name = "file_url")
    private String fileUrl;

}
