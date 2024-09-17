package com.example.bookshop.books.controllers.dto.books;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookSearchResponse {
    String id;
    String title;
    String image_url;
    Set<String> categories;
}
