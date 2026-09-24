package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.dtos.auth.AuthResponse;
import com.tavarlabs.tavarsystem.dtos.auth.LoginRequest;
import com.tavarlabs.tavarsystem.repository.UserRepository;
import com.tavarlabs.tavarsystem.service.AuthenticationService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AppKeywords.apiUrlPfx + "/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ){
        try {
            UserDetails userDetails = authenticationService.authenticate(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            String accessJwt = authenticationService.generateToken(userDetails, "access");

            AuthResponse authResponse = AuthResponse.builder()
                    .token(accessJwt)
                    .expiresIn(864000)
                    .build();

            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
