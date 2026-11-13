package com.tavarlabs.tavarsystem.dtos.item;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateItemRequest(
        @NotBlank @Size(max = 100)
        String code,

        @NotBlank @Size(max = 130)
        String name,

        @NotBlank @Size(max = 256)
        String description,

        @NotBlank @Size(max = 100)
        String category,

        @NotNull @DecimalMin(value = "0.00") @Digits(integer = 10, fraction = 2) // Synced with Item.uniPrice field db config
        BigDecimal unitPrice,

        @NotBlank @Size(max = 100)
        String taxType,

        @NotBlank @Size(max = 30)
        String unitOfMeasure,

        long quantity // quantity is initialized in 0 in the Item entity so it's not a mandatory field.
) {}
