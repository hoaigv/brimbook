package com.example.bookshop.books.mapper;

import com.example.bookshop.books.controllers.dto.chapters.ChapterCreateRequest;
import com.example.bookshop.books.controllers.dto.chapters.ChapterCreateResponse;
import com.example.bookshop.books.controllers.dto.chapters.ChapterReadResponse;
import com.example.bookshop.books.controllers.dto.chapters.ChapterUpdateRequest;
import com.example.bookshop.books.models.ChapterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ChapterMapper {

    ChapterEntity requestToEntity(ChapterCreateRequest request);


    ChapterEntity oldChapterToEntity(@MappingTarget  ChapterEntity oldChapter, ChapterUpdateRequest request);

    ChapterCreateResponse entityToResponse(ChapterEntity entity);


    ChapterReadResponse entityToReadResponse(ChapterEntity entity);
}
