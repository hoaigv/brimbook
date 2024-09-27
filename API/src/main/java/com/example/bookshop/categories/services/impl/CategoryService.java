package com.example.bookshop.categories.services.impl;

import com.example.bookshop.categories.controllers.dto.CategoryCreateRequest;
import com.example.bookshop.categories.controllers.dto.CategoryResponse;
import com.example.bookshop.categories.mappers.CategoryEntityMapper;
import com.example.bookshop.categories.models.CategoryEntity;
import com.example.bookshop.categories.repositories.CategoryRepository;
import com.example.bookshop.categories.services.ICategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService implements ICategoryService {
    CategoryRepository categoryRepository;


    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private CategoryEntityMapper categoryEntityMapper;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty");
        }
        var newCategory = modelMapper.map(request, CategoryEntity.class);
        var category = categoryRepository.save(newCategory);
        return modelMapper.map(category, CategoryResponse.class);
    }
    @Override
    public List<CategoryResponse> getAllCategories() {
        var result = categoryRepository.findAll();
        return categoryEntityMapper.requestToEntityList(result);
    }
}
