package com.tavarlabs.tavarsystem.dtos.invoice;

import com.tavarlabs.tavarsystem.enums.NcfType;
import com.tavarlabs.tavarsystem.enums.PaymentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateInvoiceRequest (

        @NotNull(message = "You must provide the customer's publicId")
        UUID customerPublicId,

        @NotNull(message = "You must provide a valid ncf type. Pick one of these (E31, E32, E33, E34, E41, E43, E44, E45, E46, E47)")
        NcfType ncfType,

        @NotNull(message = "You must provide a valid payment status. Pick one of these (PAID, PARTIAL, PENDING)")
        PaymentStatus paymentStatus,

        @Valid
        List<CreateInvoiceLineRequest> lines
){}
