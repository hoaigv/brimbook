package com.example.bookshop.categories.mappers;


import com.example.bookshop.categories.controllers.dto.CategoryResponse;

import com.example.bookshop.categories.models.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {

    CategoryResponse requestToEntity(CategoryEntity category);
    List<CategoryResponse> requestToEntityList(List<CategoryEntity> category);



}
