package com.srt.FreelanceMarketplace.service.domain.user;

import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public UserEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("user not found"));
    }

    public UserEntity getByIdWithRoles(UUID id) {
        return repository.findWithRolesById(id)
                .orElseThrow(() -> new GlobalBadRequestException("user not found"));
    }

    public List<UserEntity> getByIds(List<UUID> id) {
        return repository.findAllById(id);
    }

    public UserEntity getByIds(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("user not found"));
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

    public UserEntity getReferenceIfExistsById(UUID id) {
        if (!repository.existsById(id)) {
            throw new GlobalBadRequestException("user not found");
        }
        return getReferenceById(id);
    }
}
