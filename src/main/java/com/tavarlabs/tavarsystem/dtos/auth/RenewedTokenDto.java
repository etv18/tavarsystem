package com.tavarlabs.tavarsystem.dtos.auth;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RenewedTokenDto (String type, String message , LocalDateTime timestamp) {}
