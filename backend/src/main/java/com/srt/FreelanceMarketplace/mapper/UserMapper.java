package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.user.SignUpRequest;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserEntity signUpRequestToEntity(SignUpRequest request);
}
