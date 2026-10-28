package com.tavarlabs.tavarsystem.dtos.individual;

import com.tavarlabs.tavarsystem.enums.DocumentType;
import lombok.Builder;

@Builder
public record CreateIndividualRequestDto(
        String firstName,
        String lastName,
        DocumentType documentType,
        String documentNumber,
        String email,
        String telephone,
        String address
) {}