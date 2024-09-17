package com.example.bookshop.books.services.impl;

import com.example.bookshop.books.controllers.dto.categories.CategoryCreateRequest;
import com.example.bookshop.books.controllers.dto.categories.CategoryCreateResponse;
import com.example.bookshop.books.controllers.dto.categories.CategoryResponse;
import com.example.bookshop.books.models.CategoryEntity;
import com.example.bookshop.books.mapper.CategoryMapper;
import com.example.bookshop.books.repositories.CategoryRepository;
import com.example.bookshop.books.services.ICategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService implements ICategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    @Override
    @Transactional
    public CategoryCreateResponse createCategory(CategoryCreateRequest request) {
        var newCategory = categoryRepository.save(categoryMapper.requestToEntity(request));
        return CategoryCreateResponse.builder()
                .categoryName(newCategory.getCategoryName())
                .build();
    }

    @Override
    public CategoryResponse getAllCategories() {
        var result = categoryRepository.findAll();
        var resp = result.stream().map(
                CategoryEntity::getCategoryName
        ).collect(Collectors.toSet());
        return CategoryResponse.builder()
                .categories(resp)
                .build();
    }

}
