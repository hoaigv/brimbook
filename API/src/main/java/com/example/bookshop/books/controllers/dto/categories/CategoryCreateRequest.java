package com.example.bookshop.books.controllers.dto.categories;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryCreateRequest {
    String categoryName;
    String categoryDescription;
    String categoryCode;
}
