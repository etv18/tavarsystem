package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.entity.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    Role findRole(UUID publicId);
    List<Role> findAll();
}
