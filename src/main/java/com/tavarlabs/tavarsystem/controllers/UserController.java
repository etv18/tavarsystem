package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.dtos.user.CreateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UpdateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UserDto;
import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.mappers.UserMapper;
import com.tavarlabs.tavarsystem.service.UserService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AppKeywords.apiUrlPfx + "/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(
            @RequestBody CreateUserRequestDto dto
    ) {
        User user = userService.createUser(dto);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        List<User> users = userService.getAll();
        List<UserDto> userDtos = users.stream().map(user -> {
            return userMapper.toDto(user);
        }).toList();

        return ResponseEntity.ok(userDtos);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestBody UpdateUserRequestDto dto
    ){
        User user = userService.updateUser(dto);
        return ResponseEntity.ok(userMapper.toDto(user));
    }
}
