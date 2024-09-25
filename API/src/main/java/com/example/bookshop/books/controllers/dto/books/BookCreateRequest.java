package com.example.bookshop.books.controllers.dto.books;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCreateRequest {
    @NotBlank
    String title;

    Date publishedDate;
    @NotBlank
    String description;

    @NotNull
    Integer categoriesID;

}
