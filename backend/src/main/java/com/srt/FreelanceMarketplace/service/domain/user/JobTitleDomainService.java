package com.srt.FreelanceMarketplace.service.domain.user;

import com.srt.FreelanceMarketplace.domain.entities.JobTitleEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.JobTitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobTitleDomainService {
    private final JobTitleRepository repository;

    public void save(JobTitleEntity entity) {
        repository.save(entity);
    }

    public JobTitleEntity getReferenceIfExitsById(UUID id) {
        if (!repository.existsById(id)) {
            throw new GlobalBadRequestException("job title not found");
        }
        return repository.getReferenceById(id);
    }

    public JobTitleEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("such job title not found"));
    }
}
