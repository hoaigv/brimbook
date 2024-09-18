package com.example.bookshop.books.controllers.dto.books;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookUpdateRequest {
    Integer id;
    String title;
    String publisher;
    String description;
    Set<String> author;
    Set<String> categories;
}
