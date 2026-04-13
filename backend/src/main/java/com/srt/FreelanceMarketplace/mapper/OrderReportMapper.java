package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderReportResponse;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderReportMapper {
    OrderReportResponse toResponse(OrderReportEntity entity);
}
