package com.tavarlabs.tavarsystem.dtos.individual;

import com.tavarlabs.tavarsystem.enums.DocumentType;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UpdateIndividualRequest (
        @NotBlank(message = "Public id is required")
        UUID publicId,

        @NotBlank(message = "First name is required")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Role's name only accepts letters (a-z).")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Role's name only accepts letters (a-z).")
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

        @NotBlank(message = "Address is required")
        String address,

        boolean deleted
) {}
