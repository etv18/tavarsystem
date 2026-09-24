package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.service.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final String ACTIVE = "active";
    private final String ACCESS_TOKEN_COOKIE_KEYWORD = "accessToken";
    private final String REFRESH_TOKEN_COOKIE_KEYWORD = "refreshToken";
    private final String ACCESS_TOKEN = "access";
    private final String REFRESH_TOKEN = "refresh";
    private final Long ACCESS_JWT_EXP_IN_MS = 2000L;
    private final Long REFRESH_JWT_EXP_IN_MS = 3000L;

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public UserDetails authenticate(String username, String password) {
        // TODO: look more info about how this authenticate impl works and whats its purpose
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return (UserDetails) authentication.getPrincipal();
    }

    @Override
    public UserDetails validateToken(String accessToken, HttpServletResponse response) {
        Claims claims = parseJwtToClaims(accessToken);

        Boolean enabled = claims.get(ACTIVE, Boolean.class);
        if(!enabled) {
            throw new DisabledException("Your account is disabled, please contact your IT admin.");
        }

        String username = extractUserName(accessToken);
        List<SimpleGrantedAuthority> authorities = extractAuthorities(accessToken);
        UserDetails user = new org.springframework.security.core.userdetails.User(username, "", authorities);

        // TODO: develop validation for expired token for giving one new to the user
        return user;
    }

    @Override
    public String generateToken(UserDetails userDetails, String tokenType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(grantedAuthority -> {
                    return grantedAuthority.getAuthority();
                })
                .toList()
        );
        claims.put("type", tokenType);
        claims.put(ACTIVE, userDetails.isEnabled());
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration( new Date(System.currentTimeMillis() + setJwtExpirationTimeInMs(tokenType)) )
                .signWith(getSigningKey()) //TODO: ask why it can be done like this
                .compact();
    }

    private Key getSigningKey(){
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Long setJwtExpirationTimeInMs(String tokenType){
        if(tokenType.equalsIgnoreCase(ACCESS_TOKEN)){
            return ACCESS_JWT_EXP_IN_MS;
        }
        return REFRESH_JWT_EXP_IN_MS;
    }

    private String extractUserName(String token){
        Claims claims = parseJwtToClaims(token);
        return claims.getSubject();
    }

    private List<SimpleGrantedAuthority> extractAuthorities(String token){
        Claims claims = parseJwtToClaims(token);
        List<String> roles = (List<String>) claims.get("roles");
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority((String) r))
                .toList();
    }

    private Claims parseJwtToClaims(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
