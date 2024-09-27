package com.example.bookshop.categories.controllers.admin;

import com.example.bookshop.categories.controllers.dto.CategoryCreateRequest;
import com.example.bookshop.categories.controllers.dto.CategoryCreateResponse;
import com.example.bookshop.categories.controllers.dto.CategoryResponse1;
import com.example.bookshop.categories.services.ICategoryService;
import com.example.bookshop.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "AdminCategoryController")
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE , makeFinal = true)
public class CategoryController {
    ICategoryService categoryService;
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryCreateResponse>> create(@RequestBody @Valid CategoryCreateRequest request) {
        var category = categoryService.createCategory(request);
      var resp =  ApiResponse.<CategoryCreateResponse>builder()
                .result(category)
                .build();
      return  ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
    @GetMapping
    public ApiResponse<List<CategoryResponse1>> getAll(){
        var resp = categoryService.getAllCategories();
        return ApiResponse.<List<CategoryResponse1>>builder()
                .result(resp)
                .build();
    }
}
