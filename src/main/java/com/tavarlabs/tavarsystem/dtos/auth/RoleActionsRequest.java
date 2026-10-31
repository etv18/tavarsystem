package com.tavarlabs.tavarsystem.dtos.auth;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;
import java.util.UUID;

public record RoleActionsRequest(
        @NotBlank(message = "Role's name is required")
        String roleName,

        Set<String> actionCodes
) {}
