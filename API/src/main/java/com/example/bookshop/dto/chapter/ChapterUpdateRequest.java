package com.example.bookshop.dto.chapter;

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
