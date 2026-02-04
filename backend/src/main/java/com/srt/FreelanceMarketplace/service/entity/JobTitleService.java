package com.srt.FreelanceMarketplace.service.entity;

import com.srt.FreelanceMarketplace.domain.dto.request.JobTitleRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.JobTitleResponse;
import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;
import com.srt.FreelanceMarketplace.mapper.JobTitleMapper;
import com.srt.FreelanceMarketplace.repository.JobTitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobTitleService {
    private final JobTitleRepository repository;
    private final JobTitleMapper jobTitleMapper;

    public void save(JobTitleEntity entity) {
        repository.save(entity);
    }

    public Optional<JobTitleEntity> findByName(String name) {
        return repository.findByName(name);
    }

    public Optional<JobTitleEntity> findById(UUID id) {
        return repository.findById(id);
    }

    public List<JobTitleResponse> getAll() {
        return repository.findAll().stream()
                .map(jobTitleMapper::toResponse)
                .toList();
    }

    public void create(JobTitleRequest request) {
        JobTitleEntity jobTitle = jobTitleMapper.toEntity(request);
        save(jobTitle);
    }
}
