package com.srt.FreelanceMarketplace.service.logic;

import com.srt.FreelanceMarketplace.domain.dto.response.FreelancerResponse;
import com.srt.FreelanceMarketplace.domain.entities.FreelancerEntity;
import com.srt.FreelanceMarketplace.domain.entities.user.UserEntity;

import java.util.List;
import java.util.Optional;

public interface FreelancerService {
    void save(FreelancerEntity freelancer);
    boolean existsByUser(UserEntity user);
    Optional<FreelancerEntity> findByUser(UserEntity user);
    List<FreelancerResponse> getAll();
    FreelancerEntity getByUser(UserEntity user);
}
