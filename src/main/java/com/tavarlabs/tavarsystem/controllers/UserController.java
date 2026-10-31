package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.dtos.message.SimpleMessage;
import com.tavarlabs.tavarsystem.dtos.user.CreateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UpdateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UserDto;
import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.mappers.UserMapper;
import com.tavarlabs.tavarsystem.service.RoleService;
import com.tavarlabs.tavarsystem.service.UserService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AppKeywords.apiUrlPfx + "/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public UserDto createUser(
            @Valid @RequestBody CreateUserRequestDto dto
    ) {
        return userService.createUser(dto);
    }

    @GetMapping
    public List<UserDto> getAll(){
        return userService.getAll();
    }

    @PutMapping
    public UserDto updateUser(
            @Valid @RequestBody UpdateUserRequestDto dto,
            HttpServletResponse response
    ){
        return userService.updateUser(dto, response);
    }

    @GetMapping("/{publicId}")
    public UserDto singleUser(
            @PathVariable("publicId") String publicId
    ){
        User user = userService.getSingleUser(publicId);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{publicId}")
    public SimpleMessage deleteUser(
            @PathVariable("publicId") String publicId
    ){
        userService.deleteUser(publicId);
        return new SimpleMessage("User deleted successfully");
    }
}
