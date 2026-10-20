package com.tavarlabs.tavarsystem.mappers;

import com.tavarlabs.tavarsystem.dtos.user.UserDto;
import com.tavarlabs.tavarsystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "deleted", target = "deleted") // Explicit mapping for avoiding errors between MapStruct and Lombok
    @Mapping(source = "active", target = "active") // Same as above
    @Mapping(source = "individual.publicId", target = "individualId")
    @Mapping(source = "role.name", target = "role")
    UserDto toDto(User user);
}
