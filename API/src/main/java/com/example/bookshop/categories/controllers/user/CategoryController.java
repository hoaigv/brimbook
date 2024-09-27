package com.example.bookshop.categories.controllers.user;

import com.example.bookshop.categories.controllers.dto.CategoryResponse1;
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

    @GetMapping
    public ApiResponse<List<CategoryResponse1>> getAll(){
        var resp = categoryService.getAllCategories();
        return ApiResponse.<List<CategoryResponse1>>builder()
                .result(resp)
                .build();
    }
}
