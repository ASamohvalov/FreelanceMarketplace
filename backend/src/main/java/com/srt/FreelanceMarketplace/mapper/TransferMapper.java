package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.payment.TransferResponse;
import com.srt.FreelanceMarketplace.domain.entities.payment.TransferEntity;
import com.srt.FreelanceMarketplace.service.infrastructure.CommissionService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class TransferMapper {
    @Autowired
    protected CommissionService commissionService;

    @Mapping(target = "serviceTitle", source = "order.service.title")
    @Mapping(target = "sender.firstName", source = "order.customer.firstName")
    @Mapping(target = "sender.lastName", source = "order.customer.lastName")
    @Mapping(target = "sender.id", source = "order.customer.id")
    public abstract TransferResponse toResponse(TransferEntity transfer);

    @Mapping(target = "serviceTitle", source = "order.service.title")
    @Mapping(target = "sender.firstName", source = "order.customer.firstName")
    @Mapping(target = "sender.lastName", source = "order.customer.lastName")
    @Mapping(target = "sender.id", source = "order.customer.id")
    @Mapping(target = "amount", expression = "java(commissionService.getPriceWithoutCommission(transfer.getAmount()))")
    public abstract TransferResponse toResponseWithoutCommission(TransferEntity transfer);
}
