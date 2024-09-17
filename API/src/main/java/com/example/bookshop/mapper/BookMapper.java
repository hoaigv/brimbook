package com.example.bookshop.mapper;

import com.example.bookshop.converter.AuthorsConverter;
import com.example.bookshop.converter.CategoriesConverter;
import com.example.bookshop.converter.ChapterConverter;
import com.example.bookshop.dto.book.BookCreateRequest;
import com.example.bookshop.dto.book.BookResponse;
import com.example.bookshop.dto.book.BookSearchResponse;
import com.example.bookshop.dto.book.BookUpdateRequest;
import com.example.bookshop.entity.BookEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring" , uses = {AuthorsConverter.class, CategoriesConverter.class, ChapterConverter.class})
public interface BookMapper {
    @Mapping(target = "categories" ,qualifiedByName = {"CategoriesConverter","CategoriesToCategoriesCode"})
    @Mapping(target = "authors", qualifiedByName ={"AuthorsConverter","authorsToAuthorsName"})
    @Mapping(target = "chapters", qualifiedByName = {"ChapterConverter","ChaptersToChapterTitleResponse"})
    BookResponse bookToBookResponse(BookEntity book);


    @Mapping(target = "categories", qualifiedByName = {"CategoriesConverter","CategoriesCodeToCategories"})
    @Mapping( target = "authors", qualifiedByName ={"AuthorsConverter","authorsNameToAuthors"})
    @Mapping(target = "chapters", ignore = true)
    @Mapping(target = "image_url", ignore = true)
    BookEntity booktoBookEntity(BookCreateRequest bookCreateRequest);




    @Mapping(target = "categories", ignore = true)
    BookSearchResponse bookToBookSearchResponse(BookEntity entity);

    BookCreateRequest bookUpdateToBookCreateRequest(BookUpdateRequest bookUpdateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BookEntity updateBookToBookEntity(@MappingTarget BookEntity oldEntity, BookEntity newEntity);


}
