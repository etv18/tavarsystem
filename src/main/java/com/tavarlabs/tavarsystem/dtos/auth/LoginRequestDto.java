package com.tavarlabs.tavarsystem.dtos.auth;

import lombok.Builder;

@Builder
public record LoginRequestDto(String username, String password) { }
