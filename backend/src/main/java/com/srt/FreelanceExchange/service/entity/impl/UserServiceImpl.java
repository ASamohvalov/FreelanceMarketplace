package com.srt.FreelanceExchange.service.entity.impl;

import com.srt.FreelanceExchange.domain.entities.UserEntity;
import com.srt.FreelanceExchange.repository.UserRepository;
import com.srt.FreelanceExchange.service.entity.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public void save(UserEntity entity) {
        repository.save(entity);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
