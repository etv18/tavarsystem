package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.security.TavSysUserDetails;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AuthenticationService {
    UserDetails authenticate(String username, String password);
    UserDetails validateToken(String accessToken, String refreshToken, HttpServletResponse response);
    String generateToken(UserDetails userDetails, String tokenType);
    void setTokenOnHttpOnlyCookie(HttpServletResponse response, String type, String token);
    boolean isTokenExpired(String token);
    void clearTokensFromHttpOnlyCookies(HttpServletResponse response);
    Optional<TavSysUserDetails> currentUser();
}
