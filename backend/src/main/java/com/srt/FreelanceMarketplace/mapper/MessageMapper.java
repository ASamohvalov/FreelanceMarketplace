package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.MessageEventDTO;
import com.srt.FreelanceMarketplace.domain.dto.response.messaging.MessageResponse;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEntity;
import com.srt.FreelanceMarketplace.domain.entities.message.MessageEventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {
    @Mapping(target = "authorId", expression = "java(message.getAuthor().getId())")
    @Mapping(target = "text", expression = "java(message.getMessage())")
    MessageResponse fromEntity(MessageEntity message);

    @Mapping(target = "message", expression = "java(fromEntity(event.getMessage()))")
    MessageEventDTO fromEventEntity(MessageEventEntity event);
}
