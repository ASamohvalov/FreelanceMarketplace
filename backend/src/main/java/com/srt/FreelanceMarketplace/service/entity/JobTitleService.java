package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.dto.request.JobTitleRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.JobTitleResponse;
import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobTitleService {
    void save(JobTitleEntity entity);
    Optional<JobTitleEntity> findByName(String name);
    Optional<JobTitleEntity> findById(UUID id);
    List<JobTitleResponse> getAll();
    void create(JobTitleRequest request);
}
