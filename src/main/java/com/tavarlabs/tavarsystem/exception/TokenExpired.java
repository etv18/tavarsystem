package com.tavarlabs.tavarsystem.exception;

public class TokenExpired extends RuntimeException {
    public TokenExpired(String message) {
        super(message);
    }
}
