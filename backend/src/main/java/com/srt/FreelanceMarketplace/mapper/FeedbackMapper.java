package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.feedback.FeedbackResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.feedback.GetFeedbackResponse;
import com.srt.FreelanceMarketplace.domain.entities.feedback.FeedbackEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FeedbackMapper {
    GetFeedbackResponse toGetResponse(FeedbackEntity entity);
    FeedbackResponse toResponse(FeedbackEntity entity);
}
