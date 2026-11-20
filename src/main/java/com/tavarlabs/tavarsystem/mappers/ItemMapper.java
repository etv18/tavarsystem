package com.tavarlabs.tavarsystem.mappers;

import com.tavarlabs.tavarsystem.dtos.item.ItemResponse;
import com.tavarlabs.tavarsystem.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "taxType.name", target = "taxType")
    ItemResponse toResponse(Item item);
}
