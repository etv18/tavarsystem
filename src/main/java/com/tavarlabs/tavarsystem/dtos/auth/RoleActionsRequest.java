package com.tavarlabs.tavarsystem.dtos.auth;

import java.util.Set;
import java.util.UUID;

public record RoleActionsRequest(String roleName, Set<String> actionCodes) {}
