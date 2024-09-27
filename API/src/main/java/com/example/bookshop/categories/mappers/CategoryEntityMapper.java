package com.example.bookshop.categories.mappers;

import com.example.bookshop.categories.controllers.dto.CategoryCreateResponse;
import com.example.bookshop.categories.controllers.dto.CategoryResponse1;
import com.example.bookshop.categories.models.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {
    CategoryResponse1 requestToEntity(CategoryEntity category);
    List<CategoryResponse1> requestToEntityList(List<CategoryEntity> category);

    CategoryCreateResponse mapToCategory(CategoryEntity category);
}
