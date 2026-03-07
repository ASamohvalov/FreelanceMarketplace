package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ConversationResponse;
import com.srt.FreelanceMarketplace.domain.entities.message.ConversationMemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConversationMapper {
    @Mapping(target = "id", expression = "java(entity.getConversation().getId())")
    ConversationResponse fromConversationMemberToResponse(ConversationMemberEntity entity);
}
