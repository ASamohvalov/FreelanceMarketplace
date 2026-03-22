package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderServiceInfoResponse;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    OrderResponse toResponse(OrderEntity entity);
}
