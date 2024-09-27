package com.example.bookshop.books.mappers;

import com.example.bookshop.books.controllers.dto.books.BookResponse;
import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.categories.controllers.dto.CategoryResponse1;
import com.example.bookshop.users.controllers.dto.users.UserResponseBook;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public class BookEntityMapper {

    public static BookResponse toBookDTO(BookEntity book) {
        try {
            BookResponse bookDTO = new BookResponse();
            bookDTO.setId(book.getId());
            bookDTO.setTitle(book.getTitle());
            bookDTO.setDescription(book.getDescription());
            bookDTO.setImage_url(book.getImage_url());
            bookDTO.setPublishedDate(book.getPublishedDate());

            CategoryResponse1 categoryDTO = new CategoryResponse1();
            categoryDTO.setId(book.getCategory().getId());
            categoryDTO.setCategoryName(book.getCategory().getName());
            bookDTO.setCategory(categoryDTO);

            UserResponseBook userDTO = new UserResponseBook();
            userDTO.setId(book.getUser().getId());
            userDTO.setFirstName(book.getUser().getFirstName());
            userDTO.setLastName(book.getUser().getLastName());
            userDTO.setEmail(book.getUser().getEmail());
            userDTO.setPhone(book.getUser().getPhone());

            bookDTO.setUser(userDTO);
            return bookDTO;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<BookResponse> toBookDTOList(List<BookEntity> books) {
        return books.stream()
                .map(BookEntityMapper::toBookDTO)
                .collect(Collectors.toList());
    }

}
