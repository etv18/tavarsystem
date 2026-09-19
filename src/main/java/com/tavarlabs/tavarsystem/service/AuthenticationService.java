package com.tavarlabs.tavarsystem.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    UserDetails authenticate(String username, String password);
    UserDetails validateToken(String accessToken, HttpServletResponse response);
}
