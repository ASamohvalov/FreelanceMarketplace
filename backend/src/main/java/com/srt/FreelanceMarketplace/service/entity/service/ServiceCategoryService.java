package com.srt.FreelanceMarketplace.service.entity.service;

import com.srt.FreelanceMarketplace.domain.dto.request.service.CategoryRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.service.CategoryResponse;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceCategoryEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.ServiceCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceCategoryService {
    private final ServiceCategoryRepository repository;

    public void create(CategoryRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new GlobalBadRequestException("this name already taken");
        }
        ServiceCategoryEntity entity = ServiceCategoryEntity.builder()
                .name(request.getName())
                .build();
        repository.save(entity);
    }

    public Optional<ServiceCategoryEntity> findById(UUID id) {
        return repository.findById(id);
    }

    public List<CategoryResponse> getAll() {
        return repository.findAll().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }
}
