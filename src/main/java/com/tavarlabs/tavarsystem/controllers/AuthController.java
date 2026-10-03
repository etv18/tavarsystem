package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.dtos.auth.DtoAuthResponse;
import com.tavarlabs.tavarsystem.dtos.auth.DtoLoginRequest;
import com.tavarlabs.tavarsystem.dtos.auth.DtoRenewedToken;
import com.tavarlabs.tavarsystem.repository.UserRepository;
import com.tavarlabs.tavarsystem.security.TavSysUserDetails;
import com.tavarlabs.tavarsystem.service.AuthenticationService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

            authenticationService.clearTokensFromHttpOnlyCookies(response);

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
    public ResponseEntity<?> renewRefreshTkn() {
        return null;
    }

    @GetMapping("/renew/access-token")
    public ResponseEntity<?> renewAccessTkn(HttpServletRequest request, HttpServletResponse response) {
        try{
            authenticationService.renewAccessToken(request, response);

            return ResponseEntity.ok(
                    DtoRenewedToken.builder()
                            .message("Token renewed.")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        Cookie accessCk = WebUtils.getCookie(request, AppKeywords.accessTkn);
        Cookie refreshCk = WebUtils.getCookie(request, AppKeywords.refreshTkn);

        String accessTkn = (accessCk != null) ? accessCk.getValue() : null;
        String refreshTkn = (refreshCk != null) ? refreshCk.getValue() : null;

        if(accessTkn != null && refreshTkn != null){
            authenticationService.clearTokensFromHttpOnlyCookies(response);
            return ResponseEntity.ok(
                    Map.of("message", "User has been logged out.")
            );
        }

        return ResponseEntity
                    .badRequest()
                    .body( Map.of("message", "Theres no logged in user...") );
    }
}
