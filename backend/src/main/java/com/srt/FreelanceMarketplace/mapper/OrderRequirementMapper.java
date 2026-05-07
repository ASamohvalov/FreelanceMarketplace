package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.order.requirement.OrderRequirementResponse;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderRequirementEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderRequirementFileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderRequirementMapper {
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "orderRequirementFileIds", expression = "java(toFileList(entity.getFiles()))")
    OrderRequirementResponse toResponse(OrderRequirementEntity entity);

    default List<UUID> toFileList(List<OrderRequirementFileEntity> files) {
        return files.stream()
                .map(OrderRequirementFileEntity::getId)
                .toList();
    }
}
