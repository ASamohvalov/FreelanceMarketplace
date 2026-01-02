package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;

import java.util.Optional;

public interface JobTitleService {
    void save(JobTitleEntity entity);
    Optional<JobTitleEntity> findByName(String name);
}
