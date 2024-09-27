package com.example.bookshop.categories.services.impl;

import com.example.bookshop.categories.controllers.dto.CategoryCreateRequest;
import com.example.bookshop.categories.controllers.dto.CategoryCreateResponse;
import com.example.bookshop.categories.controllers.dto.CategoryResponse;
import com.example.bookshop.categories.controllers.dto.CategoryResponse1;
import com.example.bookshop.categories.mappers.CategoryEntityMapper;
import com.example.bookshop.categories.models.CategoryEntity;
import com.example.bookshop.categories.mappers.CategoryMapper;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService implements ICategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private CategoryMapper categoryEntityMapper;

    @Override
    @Transactional
    public CategoryCreateResponse createCategory(CategoryCreateRequest request) {
        var newCategory = categoryMapper.requestToEntity(request);
        var category = categoryRepository.save(newCategory);
        return categoryMapper.entityToResponse(category);
    }

//    @Override
//    public List<CategoryResponse1> getAllCategories() {
//        var result = categoryRepository.findAll();
//        return categoryEntityMapper.;
//    }

    @Override
    public List<CategoryResponse1> getAllCategories() {
        return List.of();
    }
}
