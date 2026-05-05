package com.srt.FreelanceMarketplace.service.application.service;

import com.srt.FreelanceMarketplace.domain.dto.IdentifierDto;
import com.srt.FreelanceMarketplace.domain.dto.request.service.CategoryRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.service.SubcategoryRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.service.CategoryResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.CategoryWithSubcategoryResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.SubcategoryResponse;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceCategoryEntity;
import com.srt.FreelanceMarketplace.domain.entities.service.ServiceSubcategoryEntity;
import com.srt.FreelanceMarketplace.error.exceptions.GlobalBadRequestException;
import com.srt.FreelanceMarketplace.repository.service.ServiceCategoryRepository;
import com.srt.FreelanceMarketplace.repository.service.ServiceSubcategoryRepository;
import com.srt.FreelanceMarketplace.service.domain.service.CategoryDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.ServiceDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.SubcategoryDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ServiceCategoryRepository repository;
    private final CategoryDomainService domainService;

    private final SubcategoryDomainService subcategoryDomainService;
    private final ServiceSubcategoryRepository serviceSubcategoryRepository;
    private final ServiceDomainService serviceDomainService;

    public IdentifierDto createCategory(CategoryRequest request) {
        if (repository.existsByName(request.getName())) {
            throw new GlobalBadRequestException("this name already taken");
        }
        ServiceCategoryEntity entity = ServiceCategoryEntity.builder()
                .name(request.getName())
                .build();
        repository.save(entity);

        return new IdentifierDto(entity.getId());
    }

    public List<CategoryResponse> getAllCategories() {
        return repository.findAll().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }

    public IdentifierDto createSubcategory(SubcategoryRequest request) {
        ServiceCategoryEntity category = domainService.getById(request.getCategoryId());
        if (serviceSubcategoryRepository.existsByNameAndCategory(request.getName(), category)) {
            throw new GlobalBadRequestException("this name already taken");
        }
        ServiceSubcategoryEntity entity = ServiceSubcategoryEntity.builder()
                .name(request.getName())
                .category(category)
                .build();
        subcategoryDomainService.save(entity);

        return new IdentifierDto(category.getId());
    }

    public void deleteSubcategory(UUID id) {
        ServiceSubcategoryEntity subcategory = subcategoryDomainService.getById(id);
        if (serviceDomainService.existsBySubcategory(subcategory)) {
            throw new GlobalBadRequestException("this subcategory already on use");
        }
        serviceSubcategoryRepository.delete(subcategory);
    }

    public void deleteCategory(UUID id) {
        ServiceCategoryEntity category = domainService.getById(id);
        if (serviceDomainService.existsByCategory(category)) {
            throw new GlobalBadRequestException("this category already on use");
        }
        repository.delete(category);
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
        return repository.findAllWithSubcategoryOrderByCreatedAtAsc().stream()
                .map(c -> new CategoryWithSubcategoryResponse(
                        c.getId(),
                        c.getName(),
                        c.getSubcategories().stream()
                                .map(sc -> new CategoryResponse(sc.getId(), sc.getName()))
                                .toList()
                )).toList();
    }
}
