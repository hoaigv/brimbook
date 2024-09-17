package com.example.bookshop.books.controllers.admin;

import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.books.controllers.dto.chapters.ChapterCreateRequest;
import com.example.bookshop.books.controllers.dto.chapters.ChapterCreateResponse;
import com.example.bookshop.books.controllers.dto.chapters.ChapterUpdateRequest;
import com.example.bookshop.books.controllers.dto.chapters.ChapterUpdateResponse;
import com.example.bookshop.books.services.IChapterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController("AdminChapterController")
@RequestMapping("/api/{bookId}/chapters")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterController {
    IChapterService chapterService;
    @PostMapping
    public ApiResponse<ChapterCreateResponse> createChapter(@PathVariable int bookId , @RequestBody ChapterCreateRequest request) {
       var result = chapterService.createChapter(bookId,request);
        return ApiResponse.<ChapterCreateResponse>builder()
                .result(result)
                .build();
    }
    @PutMapping("/{chapterId}")
    public ApiResponse<ChapterUpdateResponse> updateChapter(@PathVariable int bookId , @RequestBody ChapterUpdateRequest request) {
        var result = chapterService.updateChapter(bookId,request);
        return ApiResponse.<ChapterUpdateResponse>builder()
                .result(result)
                .build();
    }
    @GetMapping("/{chapterId}")
    public ApiResponse<Void> getChapters(@PathVariable int chapterId) {
        return ApiResponse.<Void>builder().build();
    }
    @GetMapping
    public ApiResponse<Void> getAllChapters(@PathVariable int bookId) {
        return ApiResponse.<Void>builder().build();
    }








}
