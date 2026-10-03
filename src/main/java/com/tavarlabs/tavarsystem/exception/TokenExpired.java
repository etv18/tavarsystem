package com.tavarlabs.tavarsystem.exception;

import lombok.Getter;

@Getter
public class TokenExpired extends RuntimeException {
    private final String tokenType;

    public TokenExpired(String tokenType, String message) {
        super(message);
        this.tokenType = tokenType;
    }
}
