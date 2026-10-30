package com.tavarlabs.tavarsystem.dtos.auth;

import lombok.Builder;

@Builder
public record AuthResponseDto (String accessToken, String refreshToken) {}

