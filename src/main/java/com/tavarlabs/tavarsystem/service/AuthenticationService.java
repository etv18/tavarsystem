package com.tavarlabs.tavarsystem.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(String username, String password);
    UserDetails validateToken(String accessToken, String refreshToken, HttpServletResponse response);
    String generateToken(UserDetails userDetails, String tokenType);
    void setTokenOnHttpOnlyCookie(HttpServletResponse response, String type, String token);
    boolean isTokenExpired(String token);
    void logoutUser(HttpServletResponse response);

}
