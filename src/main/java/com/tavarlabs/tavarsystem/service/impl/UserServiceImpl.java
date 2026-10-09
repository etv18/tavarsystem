package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.user.CreateUserRequestDto;
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

}
