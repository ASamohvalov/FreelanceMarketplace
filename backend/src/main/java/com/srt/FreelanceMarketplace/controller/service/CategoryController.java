package com.srt.FreelanceMarketplace.controller.service;

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

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public void createCategory(@Valid @RequestBody CategoryRequest request) {
        categoryService.createCategory(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/subcategory/create")
    public void createSubcategory(@Valid @RequestBody SubcategoryRequest request) {
        categoryService.createSubcategory(request);
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
