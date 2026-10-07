package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.user.CreateUserRequestDto;
import com.tavarlabs.tavarsystem.entity.User;

public interface AppUserServices {
    User createUser(CreateUserRequestDto userRequest);
}
