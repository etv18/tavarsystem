package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.user.CreateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UpdateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UserDto;
import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.repository.UserRepository;
import com.tavarlabs.tavarsystem.service.IndividualService;
import com.tavarlabs.tavarsystem.service.UserService;
import com.tavarlabs.tavarsystem.utils.AppFunctions;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Transactional
    @Override
    public User updateUser(UpdateUserRequestDto dto) {
        if(dto == null) {
            throw new IllegalArgumentException("You must provide accurate info about the record you want to update.");
        }

        User savedUser = getSingleUser(dto.getId().toString()); // TODO: ADD a method from the repo which allows to get not deleted users

        if(AppFunctions.differentStrings(savedUser.getUsername(), dto.getUsername())){
            savedUser.setUsername(dto.getUsername());
        }

        if(AppFunctions.differentStrings(savedUser.getPassword(), dto.getPassword())){
            savedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if(AppFunctions.differentStrings(savedUser.getRole().name(), dto.getRole().name())){
            savedUser.setRole(dto.getRole());
        }

        if(savedUser.isActive() != dto.isActive()){
            savedUser.setActive(dto.isActive());
        }

        if(savedUser.isDeleted() != dto.isDeleted()){
            savedUser.setDeleted(dto.isDeleted());
        }

        return userRepo.save(savedUser);
    }

    @Override
    public User getSingleUser(String publicId) {
        return userRepo.findUndeletedByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new EntityNotFoundException(
                        "User was not found. id = " + publicId
                ));
    }


}
