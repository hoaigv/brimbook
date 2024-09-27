
package com.example.bookshop.categories.controllers.user;


import com.example.bookshop.categories.controllers.dto.CategoryResponse;
import com.example.bookshop.categories.services.ICategoryService;
import com.example.bookshop.utils.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "UserCategoryController")
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE , makeFinal = true)
public class CategoryController {
    ICategoryService categoryService;

    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAll(){
        var resp = categoryService.getAllCategories();
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(resp)
                .build();
    }



}
