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
    /*
    * This toString() is for avoiding to show the password
    * if an object of this class at some point gets logged
    * or sth related.
    * */
    @Override
    public String toString() {
        return "CreateUserRequestDto[username=" + username + ", roleName=" + roleName
                + ", individualId=" + individualId + "]";
    }
}