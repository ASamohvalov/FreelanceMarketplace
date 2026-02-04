package com.srt.FreelanceMarketplace.service.entity.service;

import com.srt.FreelanceMarketplace.domain.dto.request.service.SubcategoryRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.service.SubcategoryResponse;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceCategoryEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceSubcategoryEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.ServiceSubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceSubcategoryService {
    private final ServiceSubcategoryRepository repository;
    private final ServiceCategoryService serviceCategoryService;

    public void create(SubcategoryRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new GlobalBadRequestException("this name already taken");
        }
        ServiceCategoryEntity category = serviceCategoryService.findById(request.getCategoryId())
                .orElseThrow(() -> new GlobalBadRequestException("this category not exists"));

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
