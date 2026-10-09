package com.tavarlabs.tavarsystem.mappers;

import com.tavarlabs.tavarsystem.dtos.individual.IndividualDto;
import com.tavarlabs.tavarsystem.entity.Individual;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IndividualMapper {

    /*
     * Explicit mapping "delete -> deleted" to avoid errors between Lombok and MapStruct
     * when mapping Individual objs into IndividualDto objs.
     * */
    @Mapping(source="deleted", target = "deleted") // NEEDED: ** READ THE NOTE ABOVE**
    @Mapping(source="user.id", target = "userId")
    IndividualDto toResponseDto(Individual individual);
}
