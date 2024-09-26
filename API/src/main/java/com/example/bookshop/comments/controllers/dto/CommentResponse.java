package com.example.bookshop.comments.controllers.dto;

import com.example.bookshop.books.controllers.dto.books.BookResponse;
import com.example.bookshop.users.controllers.dto.users.UserResponseBook;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    int id;
    Boolean isVisible;
    String commentText;
    UserResponseBook user;
    BookResponse book;
}
