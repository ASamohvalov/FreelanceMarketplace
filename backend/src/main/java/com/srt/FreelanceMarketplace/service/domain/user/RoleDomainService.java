package com.srt.FreelanceMarketplace.service.domain.user;

import com.srt.FreelanceMarketplace.domain.dto.typeEnum.RoleEnum;
import com.srt.FreelanceMarketplace.domain.entities.user.RoleEntity;
import com.srt.FreelanceMarketplace.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleDomainService {
    private final RoleRepository roleRepository;

    public void save(RoleEntity entity) {
        roleRepository.save(entity);
    }

    public RoleEntity getByName(RoleEnum name) {
        return roleRepository.getByName(name.name());
    }

    public Map<RoleEnum, RoleEntity> getAllRoles() {
        return roleRepository.findAll().stream()
                .collect(Collectors.toMap(this::getByEntity, r -> r));
    }

    private RoleEnum getByEntity(RoleEntity entity) {
        return switch (entity.getName()) {
            case "ROLE_USER" -> RoleEnum.ROLE_USER;
            case "ROLE_FREELANCER" -> RoleEnum.ROLE_FREELANCER;
            case "ROLE_ADMIN" -> RoleEnum.ROLE_ADMIN;
            case "ROLE_MODERATOR" -> RoleEnum.ROLE_MODERATOR;
            default -> throw new IllegalStateException("incorrect role: " + entity.getName());
        };
    }
}
