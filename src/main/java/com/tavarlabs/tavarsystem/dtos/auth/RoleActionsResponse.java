package com.tavarlabs.tavarsystem.dtos.auth;

import java.util.Set;
import java.util.UUID;

public record RoleActionsResponse(UUID rolePublicId, String roleName, Set<String> actions) {}
