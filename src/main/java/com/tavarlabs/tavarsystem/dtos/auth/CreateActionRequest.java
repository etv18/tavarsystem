package com.tavarlabs.tavarsystem.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record CreateActionRequest(
        @NotBlank(message = "You must provide a code")
        String code
) {}

