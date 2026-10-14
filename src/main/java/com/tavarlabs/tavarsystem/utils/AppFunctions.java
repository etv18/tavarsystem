package com.tavarlabs.tavarsystem.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class AppFunctions {
    public static boolean differentStrings(String currentVal, String newVal){
        if(currentVal == null || newVal == null) return true;
        return !currentVal.equalsIgnoreCase(newVal);
    }

    public static Claims parseJwtToClaims(String token, SecretKey signingKey){
        try {
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
