package com.example.bookshop.categories.controllers.admin;

import com.example.bookshop.categories.controllers.dto.CategoryCreateRequest;
import com.example.bookshop.categories.controllers.dto.CategoryResponse;
import com.example.bookshop.categories.services.ICategoryService;
import com.example.bookshop.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "AdminCategoryController")
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE , makeFinal = true)
public class CategoryController {
    ICategoryService categoryService;
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@RequestBody @Valid CategoryCreateRequest request) {
        var category = categoryService.createCategory(request);
      var resp =  ApiResponse.<CategoryResponse>builder()
                .result(category)
                .build();
      return  ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }


}
