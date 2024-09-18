package com.example.bookshop.books.services;

import com.example.bookshop.books.controllers.dto.chapters.*;

import java.util.List;

public interface IChapterService {
    List<ChapterTitleResponse> getAllChapterTitles(Integer bookId);
    ChapterCreateResponse createChapter(Integer bookId, ChapterCreateRequest request);
    ChapterReadResponse getChapter(Integer chapterId);
    ChapterUpdateResponse updateChapter(Integer chapterId, ChapterUpdateRequest request);

}
