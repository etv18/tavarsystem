package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.user.DtoCreateUserRequest;
import com.tavarlabs.tavarsystem.entity.User;

public interface AppUserServices {
    User createUser(DtoCreateUserRequest userRequest);
}
