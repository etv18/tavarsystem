package com.tavarlabs.tavarsystem.security;

import com.tavarlabs.tavarsystem.exception.TokenExpired;
import com.tavarlabs.tavarsystem.service.AuthenticationService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationService authenticationService;
    private final HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final boolean authRequest = request.getRequestURI().startsWith(AppKeywords.apiUrlPfx + "/auth/");

        try{

            String accessToken = extractToken(request, AppKeywords.accessTkn);
            String refreshToken = extractToken(request, AppKeywords.refreshTkn);

            if(accessToken != null && refreshToken != null && !authRequest) {

                UserDetails userDetails = authenticationService.validateToken(accessToken, refreshToken, response);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (TokenExpired | AuthenticationException ex) {
            resolver.resolveException(request, response, null, ex);
            return; // TODO: ASK ABOUT THIS
        } catch (Exception e) {
            // log the exception
            log.warn("Received invalid auth token");
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
        // Without this the request wouldn't be passed of to the next chain of filters therefore it'll never get to the controllers...
    }

    // This util function will extract the jwt token from Http-Only cookies...
    private String extractToken(HttpServletRequest request, String tokenTypeCookieKeyword){
        if(request.getCookies() != null) {
            for(Cookie cookie: request.getCookies()) {
                // it'll iterate through the http cookies and if the condition is met it'll return the value which will be the jwt string.
                if(tokenTypeCookieKeyword.equals(cookie.getName())) {
                     return cookie.getValue();
                }
            }
        }
        return null;
    }
}
