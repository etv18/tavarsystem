package com.tavarlabs.tavarsystem.dtos.user;

import com.tavarlabs.tavarsystem.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DtoCreateUserRequest {
    private String username;
    private String password;
    private RoleName roleName;
    private UUID individualId;
}
