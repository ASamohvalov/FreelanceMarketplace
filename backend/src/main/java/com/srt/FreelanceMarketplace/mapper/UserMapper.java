package com.srt.FreelanceMarketplace.mapper;

import com.srt.FreelanceMarketplace.domain.dto.RoleEnum;
import com.srt.FreelanceMarketplace.domain.dto.request.user.SignUpRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.user.GetUserResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserInfoResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.user.UserNameResponse;
import com.srt.FreelanceMarketplace.domain.entities.user.RoleEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserEntity signUpRequestToEntity(SignUpRequest request);

    UserInfoResponse entityToInfoResponse(UserEntity user);

    UserNameResponse entityToUserNameResponse(UserEntity user);

    @Mapping(target = "roles", expression = "java(toRoleEnum(user.getRoles()))")
    GetUserResponse toGetResponse(UserEntity user);

    default List<RoleEnum> toRoleEnum(List<RoleEntity> roles) {
        List<RoleEnum> result = new ArrayList<>();
        roles.forEach(role -> {
            switch (role.getName()) {
                case "ROLE_FREELANCER":
                    result.add(RoleEnum.ROLE_FREELANCER);
                    break;

                case "ROLE_USER":
                    result.add(RoleEnum.ROLE_USER);
                    break;

                case "ROLE_ADMIN":
                    result.add(RoleEnum.ROLE_ADMIN);
                    break;
            }
        });
        return result;
    }
}
