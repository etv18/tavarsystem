package com.tavarlabs.tavarsystem.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Locale;

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

    // TODO: look more info about this "<T extends Enum<T>>" like why is needed
    public static <T extends Enum<T>> boolean enumContains(Class<T> enumClass, String name){
        if(enumClass == null || name == null){
            return false;
        }

        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equalsIgnoreCase(name));
    }

    public static String upper(String s) {
        return s == null ? null : s.toUpperCase(Locale.ROOT);
    }
}
