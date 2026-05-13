package com.srt.FreelanceMarketplace.service.application.user;

import com.srt.FreelanceMarketplace.domain.dto.IdentifierDto;
import com.srt.FreelanceMarketplace.domain.dto.request.freelancer.JobTitleRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.JobTitleResponse;
import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.mapper.JobTitleMapper;
import com.srt.FreelanceMarketplace.repository.JobTitleRepository;
import com.srt.FreelanceMarketplace.service.domain.user.FreelancerDomainService;
import com.srt.FreelanceMarketplace.service.domain.user.JobTitleDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobTitleService {
    private final JobTitleRepository repository;
    private final JobTitleMapper mapper;

    private final FreelancerDomainService freelancerDomainService;
    private final JobTitleDomainService jobTitleDomainService;

    public List<JobTitleResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public IdentifierDto create(JobTitleRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new GlobalBadRequestException("this job title already exists");
        }
        JobTitleEntity jobTitle = mapper.toEntity(request);
        repository.save(jobTitle);
        return new IdentifierDto(jobTitle.getId());
    }

    public void delete(UUID id) {
        JobTitleEntity jobTitle = jobTitleDomainService.getById(id);
        if (freelancerDomainService.existsByJobTitle(jobTitle)) {
            throw new GlobalBadRequestException("this job title already on use");
        }
        repository.delete(jobTitle);
    }
}
