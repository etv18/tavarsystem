package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.user.CreateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UpdateUserRequestDto;
import com.tavarlabs.tavarsystem.entity.Role;
import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.repository.UserRepository;
import com.tavarlabs.tavarsystem.service.IndividualService;
import com.tavarlabs.tavarsystem.service.RoleService;
import com.tavarlabs.tavarsystem.service.UserService;
import com.tavarlabs.tavarsystem.utils.AppFunctions;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final RoleService roleService;

    @Transactional
    @Override
    public User createUser(CreateUserRequestDto dto) {
        if(userRepo.existsByUsername(dto.getUsername())) {
            throw new DataIntegrityViolationException("This username is already in used. Username = " + dto.getUsername());
        }

        String msgWhenIndividualNotFound =
                "Individual assignation failed when creating user. Individual ID: " + dto.getIndividualId();

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(roleService.findRoleByPublicId(dto.getRoleId()))
                .individual(indiService.getSingleIndividual(dto.getIndividualId(), msgWhenIndividualNotFound))
                .build();
        return userRepo.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepo.findUndeletedAll();
    }

    @Transactional
    @Override
    public User updateUser(UpdateUserRequestDto dto, HttpServletResponse response) {
        if(dto == null) {
            throw new IllegalArgumentException("You must provide accurate info about the record you want to update.");
        }

        User savedUser = getSingleUser(dto.getPublicId().toString()); // TODO: ADD a method from the repo which allows to get not deleted users

        if(AppFunctions.differentStrings(savedUser.getUsername(), dto.getUsername())){
            if(userRepo.existsByUsername(dto.getUsername())) {
                throw new DataIntegrityViolationException("This username is already in used. Username = " + dto.getUsername());
            }

            savedUser.setUsername(dto.getUsername());
        }

        if(AppFunctions.differentStrings(savedUser.getPassword(), dto.getPassword())){
            savedUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if(!savedUser.getRole().getPublicId().equals(dto.getRoleId())){
            Role dtoRole = roleService.findRoleByPublicId(dto.getRoleId());
            savedUser.setRole(dtoRole);
        }

        if(savedUser.isActive() != dto.isActive()){
            savedUser.setActive(dto.isActive());
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

    @Transactional
    @Override
    public void deleteUser(String publicId) {
        User user = userRepo.findUndeletedByPublicId(UUID.fromString(publicId))
                .orElseThrow(() -> new EntityNotFoundException(
                        "User was not found. id = " + publicId
                ));
        user.setDeleted(true);
        userRepo.save(user);
    }
}
