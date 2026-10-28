package com.tavarlabs.tavarsystem.dtos.individual;

import com.tavarlabs.tavarsystem.enums.DocumentType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UpdateIndividualRequest (
        UUID publicId,
        String firstName,
        String lastName,
        DocumentType documentType,
        String documentNumber,
        String email,
        String telephone,
        String address,
        boolean deleted
) {}
