package com.tavarlabs.tavarsystem.controllers;

import com.tavarlabs.tavarsystem.dtos.auth.CreateRoleRequest;
import com.tavarlabs.tavarsystem.dtos.auth.RoleActionsRequest;
import com.tavarlabs.tavarsystem.dtos.auth.RoleActionsResponse;
import com.tavarlabs.tavarsystem.dtos.message.SimpleMessage;
import com.tavarlabs.tavarsystem.service.RoleService;
import com.tavarlabs.tavarsystem.utils.AppKeywords;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = AppKeywords.apiUrlPfx + "/role")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleActionsResponse create(@RequestBody CreateRoleRequest request){
        return roleService.createRole(request);
    }

    @PostMapping("/override/action")
    public RoleActionsResponse assign(@RequestBody RoleActionsRequest request) {
        return roleService.overrideActions(request);
    }

    @DeleteMapping("/{roleName}")
    public SimpleMessage delete(
            @PathVariable("roleName") String roleName
    ){
        return roleService.deleteRole(roleName);
    }
}
