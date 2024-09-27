package com.example.bookshop.categories.mappers;

import com.example.bookshop.categories.controllers.dto.CategoryCreateRequest;
import com.example.bookshop.categories.controllers.dto.CategoryCreateResponse;
import com.example.bookshop.categories.models.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryEntity requestToEntity(CategoryCreateRequest request);

    CategoryCreateResponse entityToResponse(CategoryEntity entity);
}
