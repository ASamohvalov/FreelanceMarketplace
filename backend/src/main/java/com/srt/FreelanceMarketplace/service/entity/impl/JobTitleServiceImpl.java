package com.srt.FreelanceMarketplace.service.entity.impl;

import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;
import com.srt.FreelanceMarketplace.repository.JobTitleRepository;
import com.srt.FreelanceMarketplace.service.entity.JobTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobTitleServiceImpl implements JobTitleService {
    private final JobTitleRepository repository;

    @Override
    public void save(JobTitleEntity entity) {
        repository.save(entity);
    }

    @Override
    public Optional<JobTitleEntity> findByName(String name) {
        return repository.findByName(name);
    }
}
