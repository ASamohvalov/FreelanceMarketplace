package com.srt.FreelanceMarketplace.service.entity.user;

import com.srt.FreelanceMarketplace.domain.dto.response.FreelancerResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.FreelanceMapper;
import com.srt.FreelanceMarketplace.repository.FreelancerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FreelancerService {
    private final FreelancerRepository repository;
    private final FreelanceMapper freelanceMapper;

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

    public FreelancerEntity getByUserOrElseThrow(UserEntity user) {
        return repository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("user has FREELANCER_ROLE but hasn't freelancer entity"));
    }

    public List<FreelancerResponse> getAll() {
        return repository.findAll().stream()
                .map(freelanceMapper::freelancerEntityToResponse)
                .toList();
    }

    public FreelancerEntity getByUser(UserEntity user) {
        return repository.findByUser(user).orElseThrow(() -> new IllegalStateException("freelancer not found"));
    }
}
