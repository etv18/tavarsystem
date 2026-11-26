package com.tavarlabs.tavarsystem.entity;

import com.tavarlabs.tavarsystem.enums.EcfStatus;
import com.tavarlabs.tavarsystem.enums.NcfType;
import com.tavarlabs.tavarsystem.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceHeader extends BaseEntity {

    //TODO: FIX ENUMS

    @Column(nullable = false, length = 256, unique = true)
    private String code;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 256, unique = true)
    private String ncf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 3)
    private NcfType ncfType; // "ENUM: 31,32,33,34,41,43,44,45,46,47"

    @Enumerated(EnumType.STRING)
    @Column(name = "ecf_status", nullable = false, length = 3)
    private EcfStatus ecfStatus; // "ENUM: PENDING, SENT, ACCEPTED, REJECTED, VOIDED"

    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "total_itbis", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalItbis;

    @Column(name = "total_exempt", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalExempt;

    @Column(name = "total_discount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalDiscount = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false, length = 15)
    private String currency = "DOP";

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 30)
    private PaymentStatus paymentStatus; // PAID, PARTIAL, PENDING

    @Column(name = "provider_response", length = 1024)
    private String providerResponse;

    @Column(name = "voided_at")
    private LocalDateTime voidedAt; // English for: Fecha en la que se anula la factura
}
