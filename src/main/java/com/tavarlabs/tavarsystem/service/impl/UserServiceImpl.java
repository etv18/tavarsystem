package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.user.CreateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UserDto;
import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.repository.UserRepository;
import com.tavarlabs.tavarsystem.service.IndividualService;
import com.tavarlabs.tavarsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final IndividualService indiService;

    @Override
    public User createUser(CreateUserRequestDto dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRoleName())
                .individual(indiService.getSingleIndividual(dto.getIndividualId()))
                .build();
        return userRepo.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User updateUser(UserDto dto) {
        if(dto == null) {
            throw new IllegalArgumentException("You must provide accurate info about the record you want to update.");
        }

        User currentUser = null; // TODO: ADD a method from the repo which allows to get not deleted users

        return null;
    }

}
