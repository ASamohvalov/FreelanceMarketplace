package com.srt.FreelanceMarketplace.controller.service;

import com.srt.FreelanceMarketplace.domain.dto.request.service.CategoryRequest;
import com.srt.FreelanceMarketplace.domain.dto.request.service.SubcategoryRequest;
import com.srt.FreelanceMarketplace.domain.dto.response.service.CategoryResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.CategoryWithSubcategoryResponse;
import com.srt.FreelanceMarketplace.domain.dto.response.service.SubcategoryResponse;
import com.srt.FreelanceMarketplace.service.entity.service.ServiceCategoryService;
import com.srt.FreelanceMarketplace.service.entity.service.SubcategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final ServiceCategoryService serviceCategoryService;
    private final SubcategoryService subcategoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public void createCategory(@Valid @RequestBody CategoryRequest request) {
        serviceCategoryService.create(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/subcategory/create")
    public void createSubcategory(@Valid @RequestBody SubcategoryRequest request) {
        subcategoryService.create(request);
    }

    @GetMapping("/get_all")
    public List<CategoryResponse> getAllCategories() {
        return serviceCategoryService.getAll();
    }

    @GetMapping("/subcategory/get_all")
    public List<SubcategoryResponse> getAllSubcategories() {
        return subcategoryService.getAll();
    }

    @GetMapping("/get_all_info")
    public List<CategoryWithSubcategoryResponse> getAllSubcategoriesInfo() {
        return serviceCategoryService.getAllWithSubcategory();
    }
}
