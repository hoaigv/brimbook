package com.example.bookshop.books.controllers.dto.books;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookUpdateRequest {
    String title;
    String image_url;
    Date publishedDate;
    String description;
    int categories_ID;
    int user_ID;
}
