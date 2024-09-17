package com.example.bookshop.categories.services;

import com.example.bookshop.categories.controlers.dto.CategoryCreateRequest;
import com.example.bookshop.categories.controlers.dto.CategoryCreateResponse;
import com.example.bookshop.categories.controlers.dto.CategoryResponse;

public interface ICategoryService {
   CategoryCreateResponse createCategory(CategoryCreateRequest request);
   CategoryResponse getAllCategories();
}
