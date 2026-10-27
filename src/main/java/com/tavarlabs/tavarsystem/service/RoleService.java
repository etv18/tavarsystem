package com.tavarlabs.tavarsystem.service;

import com.tavarlabs.tavarsystem.dtos.auth.CreateRoleRequest;
import com.tavarlabs.tavarsystem.dtos.auth.RoleActionsRequest;
import com.tavarlabs.tavarsystem.dtos.auth.RoleActionsResponse;
import com.tavarlabs.tavarsystem.dtos.message.SimpleMessage;
import com.tavarlabs.tavarsystem.entity.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    Role findRoleByPublicId(UUID publicId);
    Role findRoleByName(String roleName);
    List<Role> findAll();
    RoleActionsResponse overrideActions(RoleActionsRequest rolActReq);
    RoleActionsResponse removeActions(RoleActionsRequest rolActReq);
    RoleActionsResponse createRole(CreateRoleRequest roleReq);
    SimpleMessage deleteRole(String roleName);
}
