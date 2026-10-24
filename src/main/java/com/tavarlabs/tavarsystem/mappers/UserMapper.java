package com.tavarlabs.tavarsystem.mappers;

import com.tavarlabs.tavarsystem.dtos.user.UserDto;
import com.tavarlabs.tavarsystem.entity.User;
import jdk.jfr.Name;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "deleted", target = "deleted") // Explicit mapping for avoiding errors between MapStruct and Lombok
    @Mapping(source = "active", target = "active") // Same as above
    @Mapping(source = "individual.publicId", target = "individualId")
    @Mapping(source = "role.name", target = "role")
    @Mapping(source = ".", target = "actions", qualifiedByName = "parseUserActions")
    UserDto toDto(User user);

    @Named("parseUserActions")
    default Set<String> parseUserActions(User user){
        return user.getActions().stream()
                .map(action -> action.getAction().getCode())
                .collect(Collectors.toSet());
    }
}
