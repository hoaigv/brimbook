package com.example.bookshop.categories.controllers;

import com.example.bookshop.categories.controllers.dto.CategoryResponse1;
import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.categories.controllers.dto.CategoryCreateRequest;
import com.example.bookshop.categories.controllers.dto.CategoryCreateResponse;
import com.example.bookshop.categories.controllers.dto.CategoryResponse;
import com.example.bookshop.categories.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE , makeFinal = true)
public class CategoryController {
    ICategoryService categoryService;
    @PostMapping
    public ApiResponse<CategoryResponse1> create(@RequestBody @Valid CategoryCreateRequest request) {
        var resp = categoryService.createCategory(request);
        return ApiResponse.<CategoryResponse1>builder()
                .result(resp)
                .build();
    }
    @GetMapping
    public ApiResponse<CategoryResponse1> getAll(){
        var resp = categoryService.getAllCategories();
        return ApiResponse.<CategoryResponse1>builder()
                .result(resp)
                .build();
    }
}
