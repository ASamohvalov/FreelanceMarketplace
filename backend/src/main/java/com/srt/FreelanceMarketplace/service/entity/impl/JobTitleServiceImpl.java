package com.srt.FreelanceMarketplace.service.entity.impl;

import com.srt.FreelanceMarketplace.domain.dto.request.JobTitleRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.JobTitleResponse;
import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;
import com.srt.FreelanceMarketplace.mapper.JobTitleMapper;
import com.srt.FreelanceMarketplace.repository.JobTitleRepository;
import com.srt.FreelanceMarketplace.service.entity.JobTitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobTitleServiceImpl implements JobTitleService {
    private final JobTitleRepository repository;
    private final JobTitleMapper jobTitleMapper;

    @Override
    public void save(JobTitleEntity entity) {
        repository.save(entity);
    }

    @Override
    public Optional<JobTitleEntity> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Optional<JobTitleEntity> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<JobTitleResponse> getAll() {
        return repository.findAll().stream()
                .map(jobTitleMapper::toResponse)
                .toList();
    }

    @Override
    public void create(JobTitleRequest request) {
        JobTitleEntity jobTitle = jobTitleMapper.toEntity(request);
        save(jobTitle);
    }
}
