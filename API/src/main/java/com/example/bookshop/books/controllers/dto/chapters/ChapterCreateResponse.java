package com.example.bookshop.books.controllers.dto.chapters;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterCreateResponse {
    Integer id;
    String chapterTitle;
    String content;
}
