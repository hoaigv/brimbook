package com.example.bookshop.books.controllers.dto.books;

import com.example.bookshop.books.controllers.dto.chapters.ChapterTitleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    Integer id;
    String title;
    String description;
    String resume;
    String reissueNotes;
    String image_url;
    String publisher;
    Set<String> authors;
    Set<String> categories;
    List<ChapterTitleResponse> chapters;


}
