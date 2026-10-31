package com.tavarlabs.tavarsystem.dtos.auth;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record CreateRoleRequest(
        @NotBlank(message = "Role's name is required")
        String name,
        Set<String> actionCodes   // optional
) {}
