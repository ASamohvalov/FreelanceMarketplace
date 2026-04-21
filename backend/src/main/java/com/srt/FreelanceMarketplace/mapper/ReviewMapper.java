package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.review.ReviewResponse;
import com.srt.FreelanceMarketplace.domain.entities.message.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {
    ReviewResponse toResponse(ReviewEntity entity);
}
