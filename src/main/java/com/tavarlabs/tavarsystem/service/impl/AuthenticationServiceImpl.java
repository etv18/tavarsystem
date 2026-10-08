package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.exception.TokenExpired;
import com.tavarlabs.tavarsystem.service.AuthenticationService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import com.tavarlabs.tavarsystem.utils.AppExceptionMsg;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    private final Long ACCESS_JWT_EXP_IN_MS = 1000L * 3600L * 24L * 5L; // RIGHT CONFIG, SET IT UP ON PRODUCTION: 1000L * 60L * 5L;
    private final Long REFRESH_JWT_EXP_IN_MS = 1000L * 3600L * 24L * 5L;

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
    public UserDetails validateToken(String accessToken, String refreshToken, HttpServletResponse response) {

        if(isTokenExpired(refreshToken)) {
            throw new TokenExpired(AppKeywords.refreshTkn, AppExceptionMsg.refreshTokenExpired);
        }

        if(isTokenExpired(accessToken)) {
            throw new TokenExpired(AppKeywords.accessTkn, AppExceptionMsg.accessTokenExpired);
        }

        String username = extractUserName(accessToken);
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if(!user.isEnabled()) {
            throw new DisabledException(AppExceptionMsg.disabledException);
        }

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
        claims.put("active", userDetails.isEnabled());
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration( new Date(System.currentTimeMillis() + setJwtExpirationTimeInMs(tokenType)) )
                .signWith(getSigningKey()) //TODO: ask why it can be done like this
                .compact();
    }

    @Override
    public void setTokenOnHttpOnlyCookie(HttpServletResponse response, String type, String token) {
        ResponseCookie tokenCookie = ResponseCookie.from(type, token)
                .httpOnly(true)
                .path("/")
                .sameSite("Lax")
                .maxAge(Duration.ofDays(1))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, tokenCookie.toString());
    }

    @Override
    public boolean isTokenExpired(String token) {
        /*
         * Verifies the token's signature first, then its expiration.
         * - Tampered or wrongly signed token -> SignatureException (a JwtException)
         * - Valid signature but past exp     -> ExpiredJwtException
         * - Null/blank string                -> IllegalArgumentException
         */
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return false;

        } catch (SignatureException ex) {
            throw new SignatureException(AppExceptionMsg.signatureException);
        } catch (ExpiredJwtException e) {
            System.out.println(
                    "Expired: type=" + e.getClaims().get("type")
                            + ", exp=" + e.getClaims().getExpiration()
                            + ", msg=" + e.getMessage()
            );
        }

        return true;
    }

    @Override
    public void clearTokensFromHttpOnlyCookies(HttpServletResponse response) {
        /*
         * This way I delete the cookie which has the jwt which the browser has stored,
         * so it wouldn't be sent by him automatically anymore in every request as I
         * set it up in the login method at AuthController class.
         * */
        ResponseCookie accessTokenCookie = ResponseCookie.from(AppKeywords.accessTkn, "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from(AppKeywords.refreshTkn, "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

    @Override
    public void renewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie accessCk = WebUtils.getCookie(request, AppKeywords.accessTkn);
        Cookie refreshCk = WebUtils.getCookie(request, AppKeywords.refreshTkn);

        String accessTkn = (accessCk != null) ? accessCk.getValue() : "";
        String refreshTkn = (refreshCk != null) ? refreshCk.getValue() : "";

        if(isTokenExpired(refreshTkn)) {
            throw new TokenExpired(AppKeywords.refreshTkn, AppExceptionMsg.refreshTokenExpired);
        }

        String username = extractUserName(accessTkn);
        UserDetails user = userDetailsService.loadUserByUsername(username);

        String newToken = generateToken(user, AppKeywords.accessTkn);
        setTokenOnHttpOnlyCookie(response, AppKeywords.accessTkn, newToken);
    }

    @Override
    public void renewRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie refreshCk = WebUtils.getCookie(request, AppKeywords.refreshTkn);
        String refreshTkn = (refreshCk != null) ? refreshCk.getValue() : "";

        String username = extractUserName(refreshTkn);
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if(!user.isEnabled()){
            throw new DisabledException(AppExceptionMsg.disabledException);
        }

        String newToken = generateToken(user, AppKeywords.refreshTkn);
        setTokenOnHttpOnlyCookie(response, AppKeywords.refreshTkn, newToken);
    }

    private SecretKey getSigningKey(){
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8); //look more info about this why utf_8 and ScretKey class
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Long setJwtExpirationTimeInMs(String tokenType){
        if(tokenType.equalsIgnoreCase(AppKeywords.accessTkn)){
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
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
