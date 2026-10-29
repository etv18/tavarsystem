package com.tavarlabs.tavarsystem.dtos.user;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record UserDto(
        UUID publicId,
        String username,
        String role,
        Set<String> actions,
        UUID individualId,
        boolean deleted,
        boolean active
) {}