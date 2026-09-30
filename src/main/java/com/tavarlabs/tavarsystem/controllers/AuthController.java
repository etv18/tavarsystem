package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.dtos.auth.DtoAuthResponse;
import com.tavarlabs.tavarsystem.dtos.auth.DtoLoginRequest;
import com.tavarlabs.tavarsystem.repository.UserRepository;
import com.tavarlabs.tavarsystem.service.AuthenticationService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AppKeywords.apiUrlPfx + "/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody DtoLoginRequest loginRequest,
            HttpServletResponse response
    ){
        try {
            UserDetails userDetails = authenticationService.authenticate(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            String accessJwt = authenticationService.generateToken(userDetails, AppKeywords.accessTkn);
            String refreshJwt = authenticationService.generateToken(userDetails, AppKeywords.refreshTkn);

            authenticationService.setTokenOnHttpOnlyCookie(response, AppKeywords.accessTkn, accessJwt);
            authenticationService.setTokenOnHttpOnlyCookie(response, AppKeywords.refreshTkn, refreshJwt);

            DtoAuthResponse authResponse = DtoAuthResponse.builder()
                    .accessToken(accessJwt)
                    .refreshToken(refreshJwt)
                    .build();

            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/renew/refresh-token")
    public ResponseEntity<?> refreshJWT() {
        return null;
    }


    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        authenticationService.logoutUser(response);
        return ResponseEntity.ok(
                Map.of("message", "Logged out")
        );
    }


}
