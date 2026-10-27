package com.tavarlabs.tavarsystem.mappers;

import com.tavarlabs.tavarsystem.dtos.auth.RoleActionsResponse;
import com.tavarlabs.tavarsystem.entity.Role;
import com.tavarlabs.tavarsystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    @Mapping(source = "name", target = "roleName")
    @Mapping(source = ".", target = "actions", qualifiedByName = "parseRoleActions")
    RoleActionsResponse toRoleActionsResponse(Role role);

    @Named("parseRoleActions")
    default Set<String> parseRoleActions(Role role){
        return role.getActions().stream()
                .map(action -> action.getCode())
                .collect(Collectors.toSet());
    }
}
