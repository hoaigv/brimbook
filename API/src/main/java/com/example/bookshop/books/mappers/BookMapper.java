package com.example.bookshop.books.mappers;

import com.example.bookshop.books.converters.AuthorsConverter;
import com.example.bookshop.categories.conveters.CategoriesConverter;
import com.example.bookshop.books.converters.ChapterConverter;
import com.example.bookshop.books.controllers.dto.books.BookCreateRequest;
import com.example.bookshop.books.controllers.dto.books.BookResponse;
import com.example.bookshop.books.controllers.dto.books.BookSearchResponse;
import com.example.bookshop.books.controllers.dto.books.BookUpdateRequest;
import com.example.bookshop.books.models.BookEntity;
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
