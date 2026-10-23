package com.tavarlabs.tavarsystem.service.impl;

import com.tavarlabs.tavarsystem.dtos.auth.CreateRoleRequest;
import com.tavarlabs.tavarsystem.dtos.auth.RoleActionsRequest;
import com.tavarlabs.tavarsystem.dtos.auth.RoleActionsResponse;
import com.tavarlabs.tavarsystem.entity.Action;
import com.tavarlabs.tavarsystem.entity.Role;
import com.tavarlabs.tavarsystem.repository.ActionRepository;
import com.tavarlabs.tavarsystem.repository.RoleRepository;
import com.tavarlabs.tavarsystem.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepo;
    private final ActionRepository actionRepo;

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

    @Override
    @Transactional
    public RoleActionsResponse assignActions(RoleActionsRequest rolActReq) {
        System.out.println(rolActReq.roleId());
        Role role = findRole(rolActReq.roleId());

        List<Action> actions = actionRepo.findAllByPublicIdIn(rolActReq.actionIds());
        if (actions.size() != rolActReq.actionIds().size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more actions were not found");
        }

        role.getActions().addAll(actions);   // Set ignores teh actions already assigned
        role.setUpdatedAt(LocalDateTime.now());

        Role saved = roleRepo.save(role);
        return toResponse(saved);
    }

    @Transactional
    @Override
    public RoleActionsResponse removeActions(RoleActionsRequest rolActReq) {
        Role role = findRole(rolActReq.roleId());

        role.getActions().removeIf(action -> rolActReq.actionIds().contains(action.getPublicId()));
        role.setUpdatedAt(LocalDateTime.now());

        Role saved = roleRepo.save(role);
        return toResponse(saved);
    }

    @Transactional
    @Override
    public RoleActionsResponse createRole(CreateRoleRequest roleReq) {
        String name = normalizeRoleName(roleReq.name());

        if (roleRepo.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already exists: " + name);
        }

        Role role = new Role(name);

        Set<UUID> actionIds = roleReq.actionIds();
        if (actionIds != null && !actionIds.isEmpty()) {
            List<Action> actions = actionRepo.findAllByPublicIdIn(actionIds);
            if (actions.size() != actionIds.size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "One or more actions were not found");
            }
            role.getActions().addAll(actions);
        }

        Role saved = roleRepo.save(role);
        return toResponse(saved);
    }

    private String normalizeRoleName(String raw) {
        String name = raw.trim().toUpperCase().replace(' ', '_');
        return name.startsWith("ROLE_") ? name : "ROLE_" + name;
    }

    private RoleActionsResponse toResponse(Role role) {
        return new RoleActionsResponse(
                role.getPublicId(),
                role.getName(),
                role.getActions().stream().map(action -> action.getCode()).collect(Collectors.toSet()));
    }
}
