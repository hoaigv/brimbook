package com.example.bookshop.categories.services.impl;

import com.example.bookshop.categories.controlers.dto.CategoryCreateRequest;
import com.example.bookshop.categories.controlers.dto.CategoryCreateResponse;
import com.example.bookshop.categories.controlers.dto.CategoryResponse;
import com.example.bookshop.categories.models.CategoryEntity;
import com.example.bookshop.categories.mappers.CategoryMapper;
import com.example.bookshop.categories.repositories.CategoryRepository;
import com.example.bookshop.categories.services.ICategoryService;
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
