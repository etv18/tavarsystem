package com.tavarlabs.tavarsystem.security;

import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.entity.UserAction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class TavSysUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<String> authoritiesStr = new HashSet<>(Set.of(user.getRole().getName())); // Add the role first

        // Adding actions from the user's role
        authoritiesStr.addAll(user.getRole().getActions().stream()
                    .map(roleAction -> roleAction.getCode())
                    .collect(Collectors.toSet())
        );

        for(UserAction userAction : user.getActionOverrides()){
            if(userAction.isGranted()){
                authoritiesStr.add(userAction.getAction().getCode());
            } else {
                String code = userAction.getAction().getCode();
                authoritiesStr.remove(code);
            }
        }

        System.out.println(authoritiesStr);

        return authoritiesStr.stream()
                .map(str -> new SimpleGrantedAuthority(str))
                .collect(Collectors.toSet());
    }

    @Override
    public @Nullable String getPassword() { return user.getPassword(); }

    @Override
    public String getUsername() { return user.getUsername(); }

    @Override
    public boolean isAccountNonExpired() { return UserDetails.super.isAccountNonExpired(); }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return user.isActive(); }

    public UUID getPublicId() { return user.getPublicId(); }
}
