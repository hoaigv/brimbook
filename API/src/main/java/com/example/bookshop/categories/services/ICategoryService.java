package com.example.bookshop.categories.services;

import com.example.bookshop.categories.controllers.dto.CategoryCreateRequest;
import com.example.bookshop.categories.controllers.dto.CategoryResponse;

import java.util.List;

public interface ICategoryService {
   CategoryResponse createCategory(CategoryCreateRequest request);
   List<CategoryResponse> getAllCategories();
}
