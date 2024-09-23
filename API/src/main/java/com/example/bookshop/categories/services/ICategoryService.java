package com.example.bookshop.categories.services;

import com.example.bookshop.categories.controllers.dto.CategoryCreateRequest;
import com.example.bookshop.categories.controllers.dto.CategoryCreateResponse;
import com.example.bookshop.categories.controllers.dto.CategoryResponse;

public interface ICategoryService {
   CategoryCreateResponse createCategory(CategoryCreateRequest request);
   CategoryResponse getAllCategories();
}
