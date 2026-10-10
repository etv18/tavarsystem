package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.user.CreateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UpdateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UserDto;
import com.tavarlabs.tavarsystem.entity.User;

import java.util.List;

public interface UserService {
    User createUser(CreateUserRequestDto dto);
    List<User> getAll();
    User updateUser(UpdateUserRequestDto dto);
    User getSingleUser(String publicId);
}
