package com.tavarlabs.tavarsystem.dtos.auth;

import java.util.Set;
import java.util.UUID;

public record RoleActionsResponse(UUID publicId, String roleName, Set<String> actions) {}
