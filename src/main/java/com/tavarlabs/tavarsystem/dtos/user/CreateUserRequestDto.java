package com.tavarlabs.tavarsystem.dtos.user;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateUserRequestDto(
        String username,
        String password,
        String roleName,
        UUID individualId
) {
    @Override
    public String toString() {
        return "CreateUserRequestDto[username=" + username + ", roleName=" + roleName
                + ", individualId=" + individualId + "]";
    }
}