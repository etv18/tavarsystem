package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.user.CreateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UpdateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UserDto;
import com.tavarlabs.tavarsystem.entity.User;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {
    UserDto createUser(CreateUserRequestDto dto);
    List<UserDto> getAll();
    UserDto updateUser(UpdateUserRequestDto dto, HttpServletResponse response);
    User getSingleUser(String publicId);
    void deleteUser(String publicId);
}
