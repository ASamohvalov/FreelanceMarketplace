package com.srt.FreelanceExchange.mapper;

import com.srt.FreelanceExchange.domain.dto.user.SignUpRequest;
import com.srt.FreelanceExchange.domain.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserEntity signUpRequestToEntity(SignUpRequest request);
}
