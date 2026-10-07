package com.tavarlabs.tavarsystem.mappers;

import com.tavarlabs.tavarsystem.dtos.individual.IndividualResponseDto;
import com.tavarlabs.tavarsystem.entity.Individual;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IndividualMapper {
    @Mapping(source="user.id", target = "userId")
    IndividualResponseDto toResponseDto(Individual individual);
}
