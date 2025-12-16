package com.srt.FreelanceExchange.service.entity.impl;

import com.srt.FreelanceExchange.domain.entities.RoleEntity;
import com.srt.FreelanceExchange.repository.RoleRepository;
import com.srt.FreelanceExchange.service.entity.RoleService;
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
}
