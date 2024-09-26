package com.example.bookshop.books.controllers.dto.books;

import com.example.bookshop.categories.controllers.dto.CategoryResponse;
import com.example.bookshop.users.controllers.dto.users.UserResponseBook;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    Integer id;
    String title;
    String description;
    String image_url;
    Date publishedDate;
    CategoryResponse category;
    UserResponseBook user;
}

