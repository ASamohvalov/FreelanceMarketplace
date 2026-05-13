package com.srt.FreelanceMarketplace.domain.dto.request.user;

import com.srt.FreelanceMarketplace.domain.dto.typeEnum.RoleEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EditUserRoleRequest {
    @NotNull
    private UUID id;
    @NotNull
    private List<RoleEnum> roles;
}
