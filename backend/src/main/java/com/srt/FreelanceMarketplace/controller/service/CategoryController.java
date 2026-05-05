package com.srt.FreelanceMarketplace.controller.service;

import com.srt.FreelanceMarketplace.domain.dto.IdentifierDto;
import com.srt.FreelanceMarketplace.domain.dto.request.service.CategoryRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.service.SubcategoryRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.service.CategoryResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.CategoryWithSubcategoryResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.SubcategoryResponse;
import com.srt.FreelanceMarketplace.service.application.service.CategoryService;
import com.srt.FreelanceMarketplace.service.domain.service.CategoryDomainService;
import com.srt.FreelanceMarketplace.service.domain.service.SubcategoryDomainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public IdentifierDto createCategory(@Valid @RequestBody CategoryRequest request) {
        return categoryService.createCategory(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/subcategory/create")
    public IdentifierDto createSubcategory(@Valid @RequestBody SubcategoryRequest request) {
        return categoryService.createSubcategory(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/subcategory/delete/{id}")
    public void deleteSubcategory(@PathVariable UUID id) {
        categoryService.deleteSubcategory(id);
    }

    @GetMapping("/get_all")
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/subcategory/get_all")
    public List<SubcategoryResponse> getAllSubcategories() {
        return categoryService.getAllSubcategories();
    }

    @GetMapping("/get_all_info")
    public List<CategoryWithSubcategoryResponse> getAllSubcategoriesInfo() {
        return categoryService.getAllWithSubcategory();
    }
}
