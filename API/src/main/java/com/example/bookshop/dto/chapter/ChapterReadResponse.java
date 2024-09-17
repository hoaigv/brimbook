package com.example.bookshop.dto.chapter;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterReadResponse {
    Integer id ;
    String chapterTitle ;
    String content;
}
