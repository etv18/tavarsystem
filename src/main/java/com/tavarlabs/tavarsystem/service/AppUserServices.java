package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.user.DtoCreateUserRequest;
import com.tavarlabs.tavarsystem.entity.User;

import java.util.Optional;

public interface AppUserServices {
    Optional<User> createUser(DtoCreateUserRequest userRequest);
}
