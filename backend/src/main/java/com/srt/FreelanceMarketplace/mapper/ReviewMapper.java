package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.review.ReviewResponse;
import com.srt.FreelanceMarketplace.domain.entities.message.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {
    @Mapping(target = "sendAt", source = "updatedAt")
    @Mapping(target = "service.id", source = "order.service.id")
    @Mapping(target = "service.title", source = "order.service.title")
    @Mapping(target = "author.id", source = "order.customer.id")
    @Mapping(target = "author.firstName", source = "order.customer.firstName")
    @Mapping(target = "author.lastName", source = "order.customer.lastName")
    ReviewResponse toResponse(ReviewEntity entity);
}
