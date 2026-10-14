package com.tavarlabs.tavarsystem.dtos.auth;

import lombok.Builder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

@Builder
public record AuthUser(UUID publicId, String username, boolean enabled, List<SimpleGrantedAuthority> authorities) {}