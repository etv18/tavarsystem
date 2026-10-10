package com.tavarlabs.tavarsystem.dtos.user;

import com.tavarlabs.tavarsystem.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    private String username;
    private RoleName role;
    private UUID individualId;
    private boolean deleted;
    private boolean active;
}
