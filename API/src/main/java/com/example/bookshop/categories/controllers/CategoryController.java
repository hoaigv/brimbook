package com.example.bookshop.categories.controllers;

import com.example.bookshop.categories.controllers.dto.CategoryResponse;
import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.categories.controllers.dto.CategoryCreateRequest;
import com.example.bookshop.categories.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE , makeFinal = true)
public class CategoryController {
    ICategoryService categoryService;
    @PostMapping
    public ApiResponse<CategoryResponse> create(@RequestBody @Valid CategoryCreateRequest request) {
        var resp = categoryService.createCategory(request);
        return ApiResponse.<CategoryResponse>builder()
                .result(resp)
                .build();
    }
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAll(){
        var resp = categoryService.getAllCategories();
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(resp)
                .build();
    }

}
