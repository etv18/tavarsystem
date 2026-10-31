package com.tavarlabs.tavarsystem.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateUserRequestDto(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Please indicate a role.")
        @Size(max = 50, message = "Role's name must not have more than 50 characters.")
        @Pattern(regexp = "^[a-zA-Z]+$", message = "Role's name only accepts letters (a-z).")
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