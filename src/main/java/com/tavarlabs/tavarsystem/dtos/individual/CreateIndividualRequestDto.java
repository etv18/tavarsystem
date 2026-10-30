package com.tavarlabs.tavarsystem.dtos.individual;

import com.tavarlabs.tavarsystem.enums.DocumentType;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record CreateIndividualRequestDto(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotNull(message = "Please indicate a valid document type")
        DocumentType documentType,

        @NotBlank(message = "Document number is required")
        String documentNumber,

        @Email(message = "Email format is invalid")
        @Size(max = 120, message = "Email must not have more than 120 characters.")
        String email,

        @Pattern(regexp = "^[0-9+()\\-\\s]{7,20}$", message = "Telephone format is invalid")
        String telephone,

        @Size(max = 255, message = "Address must be at most 255 characters")
        String address
) {}