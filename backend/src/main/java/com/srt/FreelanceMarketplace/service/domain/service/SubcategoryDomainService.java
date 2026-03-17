package com.srt.FreelanceMarketplace.service.domain.service;

import com.srt.FreelanceMarketplace.domain.dto.request.service.SubcategoryRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.service.SubcategoryResponse;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceCategoryEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceSubcategoryEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.ServiceSubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubcategoryDomainService {
    private final ServiceSubcategoryRepository repository;
    private final CategoryDomainService categoryDomainService;

    public void save(ServiceSubcategoryEntity entity) {
        repository.save(entity);
    }

    public ServiceSubcategoryEntity getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new GlobalBadRequestException("service with this id not found"));
    }

    public List<ServiceSubcategoryEntity> findAll() {
        return repository.findAll();
    }

    public void create(SubcategoryRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new GlobalBadRequestException("this name already taken");
        }
        ServiceCategoryEntity category = categoryDomainService.getById(request.getCategoryId());

        ServiceSubcategoryEntity entity = ServiceSubcategoryEntity.builder()
                .name(request.getName())
                .category(category)
                .build();
        repository.save(entity);
    }

    public List<SubcategoryResponse> getAll() {
        return repository.findAll().stream()
                .map(c ->
                        new SubcategoryResponse(
                                c.getId(),
                                c.getName(),
                                c.getCategory().getId()
                        )
                ).toList();
    }
}
