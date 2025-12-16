package com.srt.FreelanceExchange.service.entity;

import com.srt.FreelanceExchange.domain.entities.UserEntity;

import java.util.Optional;

public interface UserService {
    void save(UserEntity entity);
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
