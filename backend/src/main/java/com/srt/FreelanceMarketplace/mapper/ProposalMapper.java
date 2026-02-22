package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.response.messaging.ProposalResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.ServiceTitleResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import com.srt.FreelanceMarketplace.domain.entities.message.ProposalEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProposalMapper {
    @Mapping(target = "author", expression = "java(toUserNameResponse(proposal.getAuthor()))")
    @Mapping(target = "service", expression = "java(toServiceTitleResponse(proposal.getService()))")
    ProposalResponse toResponse(ProposalEntity proposal);

    UserNameResponse toUserNameResponse(UserEntity user);
    ServiceTitleResponse toServiceTitleResponse(ServiceEntity service);
}
