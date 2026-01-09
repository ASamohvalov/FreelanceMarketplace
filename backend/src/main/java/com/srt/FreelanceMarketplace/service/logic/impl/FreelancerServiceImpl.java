package com.srt.FreelanceMarketplace.service.logic.impl;

import com.srt.FreelanceMarketplace.domain.dto.response.FreelancerResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.repository.FreelancerRepository;
import com.srt.FreelanceMarketplace.service.logic.FreelancerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FreelancerServiceImpl implements FreelancerService {
    private final FreelancerRepository repository;
    private final FreelanceMapper freelanceMapper;

    @Override
    public void save(FreelancerEntity freelancer) {
        repository.save(freelancer);
    }

    @Override
    public boolean existsByUser(UserEntity user) {
        return repository.existsByUser(user);
    }

    @Override
    public Optional<FreelancerEntity> findByUser(UserEntity user) {
        return repository.findByUser(user);
    }

    @Override
    public List<FreelancerResponse> getAll() {
        return repository.findAll().stream()
                .map(freelanceMapper::freelancerEntityToResponse)
                .toList();
    }

    @Override
    public FreelancerEntity getByUser(UserEntity user) {
        return repository.findByUser(user).orElseThrow(() -> new IllegalStateException("user not found"));
    }
}
