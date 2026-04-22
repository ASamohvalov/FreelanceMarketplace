package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.order.report.ReceivedOrderReportResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.report.SentOrderReportResponse;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderReportMapper {
    @Mapping(target = "user.id", source = "customer.id")
    @Mapping(target = "user.firstName", source = "customer.firstName")
    @Mapping(target = "user.lastName", source = "customer.lastName")
    @Mapping(target = "orderId", source = "order.id")
    SentOrderReportResponse toSentResponse(OrderReportEntity entity);

    @Mapping(target = "freelancer.firstName", source = "freelancer.user.firstName")
    @Mapping(target = "freelancer.lastName", source = "freelancer.user.lastName")
    @Mapping(target = "freelancer.jobTitle", source = "freelancer.jobTitle.name")
    @Mapping(target = "orderId", source = "order.id")
    ReceivedOrderReportResponse toReceivedResponse(OrderReportEntity entity);
}
