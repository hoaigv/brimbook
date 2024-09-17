package com.example.bookshop.books.services;

import com.example.bookshop.books.controllers.dto.categories.CategoryCreateRequest;
import com.example.bookshop.books.controllers.dto.categories.CategoryCreateResponse;
import com.example.bookshop.books.controllers.dto.categories.CategoryResponse;

public interface ICategoryService {
   CategoryCreateResponse createCategory(CategoryCreateRequest request);
   CategoryResponse getAllCategories();
}
