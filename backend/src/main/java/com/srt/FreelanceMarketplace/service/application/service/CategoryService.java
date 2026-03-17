package com.srt.FreelanceMarketplace.service.application.service;

import com.srt.FreelanceMarketplace.domain.dto.request.service.CategoryRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.service.SubcategoryRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.service.CategoryResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.CategoryWithSubcategoryResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.SubcategoryResponse;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceCategoryEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceSubcategoryEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.ServiceCategoryRepository;
import com.srt.FreelanceMarketplace.service.domain.service.CategoryDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.SubcategoryDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ServiceCategoryRepository repository;
    private final CategoryDomainService domainService;

    private final SubcategoryDomainService subcategoryDomainService;

    public void createCategory(CategoryRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new GlobalBadRequestException("this name already taken");
        }
        ServiceCategoryEntity entity = ServiceCategoryEntity.builder()
                .name(request.getName())
                .build();
        repository.save(entity);
    }

    public List<CategoryResponse> getAllCategories() {
        return repository.findAll().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }

    public void createSubcategory(SubcategoryRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new GlobalBadRequestException("this name already taken");
        }
        ServiceCategoryEntity category = domainService.getById(request.getCategoryId());
        ServiceSubcategoryEntity entity = ServiceSubcategoryEntity.builder()
                .name(request.getName())
                .category(category)
                .build();
        subcategoryDomainService.save(entity);
    }

    public List<SubcategoryResponse> getAllSubcategories() {
        return subcategoryDomainService.findAll().stream()
                .map(c ->
                        new SubcategoryResponse(
                                c.getId(),
                                c.getName(),
                                c.getCategory().getId()
                        )
                ).toList();
    }

    public List<CategoryWithSubcategoryResponse> getAllWithSubcategory() {
        return repository.findAll().stream()
                .map(c -> new CategoryWithSubcategoryResponse(
                        c.getId(),
                        c.getName(),
                        c.getSubcategories().stream()
                                .map(sc -> new CategoryResponse(sc.getId(), sc.getName()))
                                .toList()
                )).toList();
    }
}
