package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.dto.RoleEnum;
import com.srt.FreelanceMarketplace.domain.entities.user.RoleEntity;
import com.srt.FreelanceMarketplace.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public void save(RoleEntity entity) {
        roleRepository.save(entity);
    }

    public RoleEntity getByName(RoleEnum name) {
        return roleRepository.getByName(name.name());
    }
}
