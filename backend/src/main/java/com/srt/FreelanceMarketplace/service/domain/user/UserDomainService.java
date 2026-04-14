package com.srt.FreelanceMarketplace.service.domain.user;

import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.mapper.UserMapper;
import com.srt.FreelanceMarketplace.repository.UserRepository;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.infrastructure.AuthHelperService;
import com.srt.FreelanceMarketplace.util.impl.ImageStorageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDomainService {
    private final UserRepository repository;

    public void save(UserEntity entity) {
        repository.save(entity);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<UserEntity> findByEmailWithRoles(String email) {
        return repository.findByEmailWithRoles(email);
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public UserEntity getReferenceById(UUID id) {
        return repository.getReferenceById(id);
    }
}
