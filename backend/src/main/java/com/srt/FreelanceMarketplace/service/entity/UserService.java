package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.dto.request.user.JwtRequest;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;

import java.util.Optional;

public interface UserService {
    void save(UserEntity entity);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailWithRoles(String email);
    boolean existsByEmail(String email);
    void logout(JwtRequest request);
}
