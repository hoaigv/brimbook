package com.example.bookshop.books.controllers.dto.books;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCreateResponse {
    Integer id;
    String title;
}
