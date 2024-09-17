package com.example.bookshop.service.impl;

import com.example.bookshop.dto.chapter.*;
import com.example.bookshop.dto.notification.PushNotificationRequest;
import com.example.bookshop.entity.BookEntity;
import com.example.bookshop.entity.ChapterEntity;
import com.example.bookshop.exception.CustomRunTimeException;
import com.example.bookshop.exception.ErrorCode;
import com.example.bookshop.mapper.ChapterMapper;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.ChapterRepository;
import com.example.bookshop.service.IChapterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterService implements IChapterService {
    ChapterRepository chapterRepository;
    BookRepository bookRepository;
    ChapterMapper chapterMapper;
    FCMService fcmService;

    @Override
    public List<ChapterTitleResponse> getAllChapterTitles(Integer bookId) {
        List<ChapterTitleResponse> chapterTitleResponses = new ArrayList<>();
        var chapters = chapterRepository.findByBookId(bookId);
        if (!chapters.isEmpty()) {
            chapters.forEach(chapterEntity ->
                    chapterTitleResponses.add(
                            ChapterTitleResponse.builder()
                                    .id(chapterEntity.getId())
                                    .chapterTitle(chapterEntity.getChapterTitle())
                                    .build()
                    ));
        }

        return chapterTitleResponses;
    }

    @Override
    public ChapterReadResponse getChapter(Integer chapterId) {
        var chapter = chapterRepository.findById(chapterId).orElseThrow(
                () -> new CustomRunTimeException(ErrorCode.CHAPTER_NOT_FOUND)
        );

        return chapterMapper.entityToReadResponse(chapter);
    }

    @Override
    @Transactional
    public ChapterCreateResponse createChapter(Integer bookId, ChapterCreateRequest request) {
        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND));

        ChapterEntity chapterEntity = chapterMapper.requestToEntity(request);
        chapterEntity.setBook(bookEntity);
        var result = chapterRepository.save(chapterEntity);

        bookEntity.getChapters().add(chapterEntity);
        bookRepository.save(bookEntity);
        var message = PushNotificationRequest
                .builder()
                .title(chapterEntity.getChapterTitle())
                .message("New Chapter :" + chapterEntity.getChapterTitle())
                .topic(String.valueOf(bookId))
                .build();
        fcmService.sendMessageToTopic(message);
        return chapterMapper.entityToResponse(result);
    }

    @Override
    @Transactional
    public ChapterUpdateResponse updateChapter(Integer chapterId, ChapterUpdateRequest request) {
        var oldChapter = chapterRepository.findById(chapterId).orElseThrow(
                () -> new CustomRunTimeException(ErrorCode.CHAPTER_NOT_FOUND)
        );
        var chapterUpdate = chapterMapper.oldChapterToEntity(oldChapter, request);

        chapterRepository.save(chapterUpdate);

        return ChapterUpdateResponse.builder()
                .chapterTitle(chapterUpdate.getChapterTitle())
                .build();
    }


}
