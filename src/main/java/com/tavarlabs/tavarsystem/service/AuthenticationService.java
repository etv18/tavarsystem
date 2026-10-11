package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.entity.Token;
import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.enums.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(String username, String password);
    UserDetails validateToken(String accessToken, String refreshToken, HttpServletResponse response);
    String generateToken(UserDetails userDetails, String tokenType);
    void setTokenOnHttpOnlyCookie(HttpServletResponse response, String type, String token);
    boolean isTokenExpired(String token);
    void clearTokensFromHttpOnlyCookies(HttpServletResponse response);
    void renewAccessToken(HttpServletRequest request, HttpServletResponse response);
    void renewRefreshToken(HttpServletRequest request, HttpServletResponse response);
    Token saveToken(User user, TokenType type, String rawValue);
}
