package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.user.CreateUserRequestDto;
import com.tavarlabs.tavarsystem.entity.User;

import java.util.List;

public interface UserService {
    User createUser(CreateUserRequestDto dto);
    List<User> getAll();
}
