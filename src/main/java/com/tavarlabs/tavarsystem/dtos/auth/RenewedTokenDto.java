package com.tavarlabs.tavarsystem.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RenewedTokenDto {
    private String type;
    private String message;
    private LocalDateTime timestamp;
}
