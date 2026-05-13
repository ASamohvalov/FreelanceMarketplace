package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.typeEnum.RoleEnum;
import com.srt.FreelanceMarketplace.domain.dto.request.user.SignUpRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.user.GetUserProfileResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.GetUserResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import com.srt.FreelanceMarketplace.domain.entities.user.RoleEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserEntity signUpRequestToEntity(SignUpRequest request);

    UserInfoResponse entityToInfoResponse(UserEntity user);

    UserNameResponse entityToUserNameResponse(UserEntity user);

    GetUserResponse toGetResponse(UserEntity user);

    GetUserProfileResponse toGetProfileResponse(UserEntity user);

    default RoleEnum roleEntityToRoleEnum(RoleEntity role) {
        if (role == null || role.getName() == null) {
            return null;
        }

        return switch (role.getName()) {
            case "ROLE_FREELANCER" -> RoleEnum.ROLE_FREELANCER;
            case "ROLE_USER" -> RoleEnum.ROLE_USER;
            case "ROLE_ADMIN" -> RoleEnum.ROLE_ADMIN;
            case "ROLE_MODERATOR" -> RoleEnum.ROLE_MODERATOR;
            default -> throw new IllegalArgumentException("Unknown role: " + role.getName());
        };
    }
}
