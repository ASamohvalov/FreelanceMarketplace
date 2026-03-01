package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.messaging.NotificationResponse;
import com.srt.FreelanceMarketplace.domain.entities.message.NotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {
    NotificationResponse toResponse(NotificationEntity notification);
}
