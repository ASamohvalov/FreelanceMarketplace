package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.order.GetOrderResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.order.OrderResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.order.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    OrderResponse toResponse(OrderEntity entity);

    @Mapping(target = "serviceId", source = "service.id")
    @Mapping(target = "serviceTitle", source = "service.title")
    GetOrderResponse toGetResponse(OrderEntity entity);

    default UserNameResponse freelancerEntityToUserNameResponse(FreelancerEntity freelancer) {
        return new UserNameResponse(
                freelancer.getUser().getId(),
                freelancer.getUser().getFirstName(),
                freelancer.getUser().getLastName()
        );
    }
}
