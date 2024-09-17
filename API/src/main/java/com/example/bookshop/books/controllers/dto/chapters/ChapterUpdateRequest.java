package com.example.bookshop.books.controllers.dto.chapters;

import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterUpdateRequest {
    String chapterTitle;
    @Lob
    String content;

}
