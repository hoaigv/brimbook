package com.example.bookshop.converter;

import com.example.bookshop.dto.chapter.ChapterTitleResponse;
import com.example.bookshop.entity.ChapterEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;


@Named("ChapterConverter")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Component
public class ChapterConverter {
    @Named("ChaptersToChapterTitleResponse")
    public List<ChapterTitleResponse> chaptersToChapterTitleResponse(List<ChapterEntity> chapters){
        return chapters.stream().map(
                chapter -> ChapterTitleResponse.builder()
                        .id(chapter.getId())
                        .chapterTitle(chapter.getChapterTitle())
                        .build()
        ).toList();
    }
}
