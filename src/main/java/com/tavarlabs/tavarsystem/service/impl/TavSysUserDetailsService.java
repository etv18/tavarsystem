package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.repository.UserRepository;
import com.tavarlabs.tavarsystem.security.TavSysUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class TavSysUserDetailsService implements UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
        return new TavSysUserDetails(user);
    }
}
