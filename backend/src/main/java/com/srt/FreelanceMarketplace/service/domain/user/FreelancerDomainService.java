package com.srt.FreelanceMarketplace.service.domain.user;

import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.FreelancerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FreelancerDomainService {
    private final FreelancerRepository repository;

    public void save(FreelancerEntity freelancer) {
        repository.save(freelancer);
    }

    public boolean existsByUser(UserEntity user) {
        return repository.existsByUser(user);
    }

    public FreelancerEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("such freelancer not found"));
    }

    public FreelancerEntity getByIdWithJobTitle(UUID id) {
        return repository.findByIdWithJobTitle(id)
                .orElseThrow(() -> new GlobalBadRequestException("such freelancer not found"));
    }

    public FreelancerEntity getByUserOrElseThrow(UserEntity user) {
        return repository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("user has FREELANCER_ROLE but hasn't freelancer entity"));
    }

    public FreelancerEntity getByUser(UserEntity user) {
        return repository.findByUser(user).orElseThrow(() -> new IllegalStateException("freelancer not found"));
    }
}
