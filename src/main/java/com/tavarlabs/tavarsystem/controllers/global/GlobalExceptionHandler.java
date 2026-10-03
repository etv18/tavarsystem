package com.tavarlabs.tavarsystem.controllers.global;

import com.tavarlabs.tavarsystem.dtos.exception.DtoExceptionBody;
import com.tavarlabs.tavarsystem.exception.TokenExpired;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        DtoExceptionBody.builder()
                                .error("INVALID CREDENTIALS")
                                .message(ex.getMessage())
                                .build()

                );
    }


    @ExceptionHandler(TokenExpired.class)
    public ResponseEntity<?> handleTokenExpired(TokenExpired ex) {
        String code = AppKeywords.accessTkn.equals(ex.getTokenType())
                ? "ACCESS_TOKEN_EXPIRED"
                : "REFRESH_TOKEN_EXPIRED";

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        DtoExceptionBody.builder()
                                .error(code)
                                .message(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleDisabledException(DisabledException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        DtoExceptionBody.builder()
                                .error("DISABLED ACCOUNT")
                                .message(ex.getMessage())
                                .build()
                );
    }

}
