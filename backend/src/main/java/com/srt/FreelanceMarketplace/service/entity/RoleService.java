package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.entities.user.RoleEntity;

public interface RoleService {
    void save(RoleEntity entity);
    RoleEntity getByName(String name);
}
