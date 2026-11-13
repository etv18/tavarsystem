package com.tavarlabs.tavarsystem.dtos.item;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemResponse(
        UUID publicId,
        String code,
        String name,
        String description,
        String category,
        BigDecimal unitPrice,
        String unitOfMeasure,
        long quantity,
        boolean active
) {}
