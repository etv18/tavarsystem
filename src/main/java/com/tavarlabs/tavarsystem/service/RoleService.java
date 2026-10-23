package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.auth.CreateRoleRequest;
import com.tavarlabs.tavarsystem.dtos.auth.RoleActionsRequest;
import com.tavarlabs.tavarsystem.dtos.auth.RoleActionsResponse;
import com.tavarlabs.tavarsystem.entity.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    Role findRole(UUID publicId);
    List<Role> findAll();
    RoleActionsResponse assignActions(RoleActionsRequest rolActReq);
    RoleActionsResponse removeActions(RoleActionsRequest rolActReq);
    RoleActionsResponse createRole(CreateRoleRequest roleReq);
}
