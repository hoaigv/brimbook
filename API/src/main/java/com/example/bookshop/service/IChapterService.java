package com.example.bookshop.service;

import com.example.bookshop.dto.chapter.*;

import java.util.List;

public interface IChapterService {
    List<ChapterTitleResponse> getAllChapterTitles(Integer bookId);
    ChapterCreateResponse createChapter( Integer bookId,ChapterCreateRequest request);
    ChapterReadResponse getChapter(Integer chapterId);
    ChapterUpdateResponse updateChapter(Integer chapterId, ChapterUpdateRequest request);

}
