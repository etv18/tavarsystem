package com.tavarlabs.tavarsystem.dtos.auth;

import java.util.Set;
import java.util.UUID;

public record RoleActionsRequest(UUID roleId, Set<UUID> actionIds) {}
