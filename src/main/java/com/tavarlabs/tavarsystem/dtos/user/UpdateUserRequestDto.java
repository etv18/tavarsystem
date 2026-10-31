package com.tavarlabs.tavarsystem.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record UpdateUserRequestDto(
        @NotBlank(message = "You must provide user's publicId value.")
        UUID publicId,

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Please indicate a role.")
        String roleName,

        @NotBlank(message = "You must provide individual's publicId which will be associated to this user.")
        UUID individualId,

        Set<String> actions,
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