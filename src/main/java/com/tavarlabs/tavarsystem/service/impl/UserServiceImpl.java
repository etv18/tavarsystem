package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.auth.AuthUser;
import com.tavarlabs.tavarsystem.dtos.user.CreateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UpdateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UserDto;
import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.repository.UserRepository;
import com.tavarlabs.tavarsystem.security.TavSysUserDetails;
import com.tavarlabs.tavarsystem.service.AuthenticationService;
import com.tavarlabs.tavarsystem.service.IndividualService;
import com.tavarlabs.tavarsystem.service.UserService;
import com.tavarlabs.tavarsystem.utils.AppFunctions;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthenticationService authenticationService;

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
                .role(dto.getRoleName())
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

        // Refactor this so it compares against db registered roles
        if(AppFunctions.differentStrings(savedUser.getRole().name(), dto.getRole().name())){
            savedUser.setRole(dto.getRole());
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
