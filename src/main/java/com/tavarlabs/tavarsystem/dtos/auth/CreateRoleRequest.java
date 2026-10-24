package com.tavarlabs.tavarsystem.dtos.auth;

import java.util.Set;
import java.util.UUID;

public record CreateRoleRequest(
       String name,
       Set<String> actionCodes   // optional
) {}
