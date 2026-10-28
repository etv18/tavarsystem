package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.user.CreateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UpdateUserRequestDto;
import com.tavarlabs.tavarsystem.dtos.user.UserDto;
import com.tavarlabs.tavarsystem.entity.Action;
import com.tavarlabs.tavarsystem.entity.Role;
import com.tavarlabs.tavarsystem.entity.User;
import com.tavarlabs.tavarsystem.entity.UserAction;
import com.tavarlabs.tavarsystem.mappers.UserMapper;
import com.tavarlabs.tavarsystem.repository.ActionRepository;
import com.tavarlabs.tavarsystem.repository.UserActionRepository;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final UserActionRepository userActionRepo;
    private final ActionRepository actionRepo;
    private final PasswordEncoder passwordEncoder;
    private final IndividualService indiService;
    private final RoleService roleService;

    @Transactional
    @Override
    public UserDto createUser(CreateUserRequestDto dto) {
        if(userRepo.existsByUsername(dto.getUsername())) {
            throw new DataIntegrityViolationException("This username is already in used. Username = " + dto.getUsername());
        }

        String msgWhenIndividualNotFound =
                "Individual assignation failed when creating user. Individual ID: " + dto.getIndividualId();

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(roleService.findRoleByName(dto.getRoleName()))
                .individual(indiService.getSingleIndividual(dto.getIndividualId(), msgWhenIndividualNotFound))
                .build();
        return userMapper.toDto(userRepo.save(user));
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepo.findUndeletedAll();
        return users.stream().map(userMapper::toDto).toList();
    }

    @Transactional
    @Override
    public UserDto updateUser(UpdateUserRequestDto dto, HttpServletResponse response) {
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

        // Updating actions
        Set<String> savedUserActions = savedUser.getActions().stream()
                .map(userAction -> userAction.getAction().getCode())
                .collect(Collectors.toSet());
        if(!savedUserActions.equals(dto.getActions())){
            userActionRepo.deleteAllByUserId(savedUser.getId());
            Set<Action> savedActions = new HashSet<>(actionRepo.findAllByCodeIn(dto.getActions()));

            Set<UserAction> newUserActions = savedActions.stream().map(action -> {
                return new UserAction(savedUser, action, true);
            }).collect(Collectors.toSet());

            savedUser.setActions(newUserActions);
        }

        return userMapper.toDto(userRepo.save(savedUser));
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
