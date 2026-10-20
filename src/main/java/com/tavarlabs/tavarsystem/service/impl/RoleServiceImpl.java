package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.entity.Role;
import com.tavarlabs.tavarsystem.repository.RoleRepository;
import com.tavarlabs.tavarsystem.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepo;

    @Override
    public Role findRole(UUID publicId) {
        return roleRepo.findByPublicId(publicId).orElseThrow(
                () -> new EntityNotFoundException("Role was not found. Given id = "+publicId)
        );
    }

    @Override
    public List<Role> findAll() {
        return roleRepo.findAll();
    }
}
