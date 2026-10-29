package com.tavarlabs.tavarsystem.dtos.user;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record UpdateUserRequestDto(
        UUID publicId,
        String username,
        String password,
        String roleName,
        Set<String> actions,
        UUID individualId,
        boolean deleted,
        boolean active
) {
    @Override
    public String toString() {
        return "UpdateUserRequestDto[publicId=" + publicId + ", username=" + username
                + ", roleName=" + roleName + ", actions=" + actions
                + ", individualId=" + individualId + ", deleted=" + deleted
                + ", active=" + active + "]";
    }
}