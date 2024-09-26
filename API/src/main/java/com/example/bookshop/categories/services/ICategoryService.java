package com.example.bookshop.categories.services;

import com.example.bookshop.categories.controllers.dto.CategoryCreateRequest;
import com.example.bookshop.categories.controllers.dto.CategoryCreateResponse;
import com.example.bookshop.categories.controllers.dto.CategoryResponse1;

import java.util.List;

public interface ICategoryService {
   CategoryResponse1 createCategory(CategoryCreateRequest request);
   List<CategoryResponse1> getAllCategories();
}
