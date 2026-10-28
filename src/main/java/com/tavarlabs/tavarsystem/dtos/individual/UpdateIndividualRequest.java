package com.tavarlabs.tavarsystem.dtos.individual;

import com.tavarlabs.tavarsystem.enums.DocumentType;

public record UpdateIndividualRequest (
        String publicId,
        String firstName,
        String lastName,
        DocumentType documentType,
        String documentNumber,
        String email,
        String telephone,
        String address,
        boolean deleted
) {}
