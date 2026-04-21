package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.review.ReviewResponse;
import com.srt.FreelanceMarketplace.domain.entities.message.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {
    @Mapping(target = "sendAt", source = "updatedAt")
    ReviewResponse toResponse(ReviewEntity entity);
}
