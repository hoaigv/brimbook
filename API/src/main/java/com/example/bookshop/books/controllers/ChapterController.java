package com.example.bookshop.books.controllers;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("AdminChapterController")
@RequestMapping("/api/{bookId}/chapters")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChapterController {
    IChapterService chapterService;
    @PostMapping
    public ResponseEntity<ApiResponse<ChapterCreateResponse>> createChapter(@PathVariable int bookId , @RequestBody ChapterCreateRequest request) {
       var result =ApiResponse.<ChapterCreateResponse>builder()
               .result(chapterService.createChapter(bookId,request))
               .build();

        return ResponseEntity.ok(result);
    }
    @PutMapping("/{chapterId}")
    public ResponseEntity<ApiResponse<ChapterUpdateResponse>> updateChapter(@PathVariable int bookId , @RequestBody ChapterUpdateRequest request) {
               chapterService.updateChapter(bookId,request);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{chapterId}")
    public ResponseEntity<ApiResponse<Void>> getChapters(@PathVariable int chapterId) {
        var resp = ApiResponse.<Void>builder().build();
        return ResponseEntity.ok(resp);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<Void>> getAllChapters(@PathVariable int bookId) {
        var resp = ApiResponse.<Void>builder().build();
        return ResponseEntity.ok(resp);
    }








}
