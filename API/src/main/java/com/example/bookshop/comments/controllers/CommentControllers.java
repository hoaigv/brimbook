package com.example.bookshop.comments.controllers;

import com.example.bookshop.comments.controllers.dto.CommentRequest;
import com.example.bookshop.comments.controllers.dto.CommentResponse;
import com.example.bookshop.comments.services.ICommentService;
import com.example.bookshop.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentControllers {
    ICommentService commentService;

    @GetMapping("/{bookId}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComment(@PathVariable("bookId") int bookId) {
        var resp = ApiResponse.<List<CommentResponse>>builder()
                .result(commentService.getCommentsByIdBook(bookId))
                .build();
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/{bookId}")
//    @SendTo("/topic/comments")
    public ResponseEntity<CommentResponse> sendComment(
            @RequestBody @Valid CommentRequest comment,
            @PathVariable("bookId") Integer bookId){
        var resp = ApiResponse.<CommentResponse>builder()
                .result(commentService.sendComment(comment,bookId))
                .build();
        return ResponseEntity.ok(resp.getResult());
    }
}
