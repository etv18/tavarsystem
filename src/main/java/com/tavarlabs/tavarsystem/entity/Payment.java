package com.tavarlabs.tavarsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "invoice_header_id")
    private InvoiceHeader invoiceHeader;

    @Column(nullable = false, length = 100, unique = true)
    private String method; // ENUM: CASH, CARD, TRANSFER

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
}
