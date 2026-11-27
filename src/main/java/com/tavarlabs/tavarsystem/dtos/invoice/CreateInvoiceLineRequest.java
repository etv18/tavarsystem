package com.tavarlabs.tavarsystem.dtos.invoice;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateInvoiceLineRequest (
        @NotNull(message = "You must provide the item's publicId")
        UUID itemPublicId,

        @NotNull @DecimalMin(value = "1.00") @Digits(integer = 10, fraction = 2)
        BigDecimal quantity,

        @NotNull @DecimalMin(value = "0.00") @Digits(integer = 10, fraction = 2)
        BigDecimal discount
){}