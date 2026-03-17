package com.srt.FreelanceMarketplace.service.application.user;

import com.srt.FreelanceMarketplace.domain.dto.request.JobTitleRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.JobTitleResponse;
import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.JobTitleMapper;
import com.srt.FreelanceMarketplace.repository.JobTitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobTitleService {
    private final JobTitleRepository repository;
    private final JobTitleMapper mapper;

    public List<JobTitleResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void create(JobTitleRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new GlobalBadRequestException("this job title already exists");
        }
        JobTitleEntity jobTitle = mapper.toEntity(request);
        repository.save(jobTitle);
    }
}
