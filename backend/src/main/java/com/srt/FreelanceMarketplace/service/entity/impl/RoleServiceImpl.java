package com.srt.FreelanceMarketplace.service.entity.impl;

import com.srt.FreelanceMarketplace.domain.entities.user.RoleEntity;
import com.srt.FreelanceMarketplace.repository.RoleRepository;
import com.srt.FreelanceMarketplace.service.entity.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public void save(RoleEntity entity) {
        roleRepository.save(entity);
    }

    @Override
    public RoleEntity getByName(String name) {
        return roleRepository.getByName(name);
    }
}
