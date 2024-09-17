package com.example.bookshop.books.mapper;

import com.example.bookshop.books.controllers.dto.categories.CategoryCreateRequest;
import com.example.bookshop.books.models.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryEntity requestToEntity(CategoryCreateRequest request);
}
