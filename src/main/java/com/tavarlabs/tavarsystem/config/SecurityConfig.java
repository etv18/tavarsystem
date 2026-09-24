package com.tavarlabs.tavarsystem.config;

import com.tavarlabs.tavarsystem.repository.UserRepository;
import com.tavarlabs.tavarsystem.security.AuthEntryPoint;
import com.tavarlabs.tavarsystem.security.JwtAuthenticationFilter;
import com.tavarlabs.tavarsystem.service.AuthenticationService;
import com.tavarlabs.tavarsystem.service.impl.TavSysUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public AuthEntryPoint authEntryPoint() { return new AuthEntryPoint(); }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService) {
        return new JwtAuthenticationFilter(authenticationService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return new TavSysUserDetailsService(userRepo);
    }
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .exceptionHandling(exception ->
                    exception.authenticationEntryPoint(authEntryPoint())
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
