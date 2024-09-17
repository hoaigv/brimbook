package com.example.bookshop.books.controllers.user;

import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.books.controllers.dto.comments.CommentRequest;
import com.example.bookshop.books.controllers.dto.comments.CommentResponse;
import com.example.bookshop.books.services.ICommentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("UserCommentController")
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentController {
     ICommentService commentService;
     @GetMapping("/{bookId}")
     public ApiResponse<List<CommentResponse>> getComment(@PathVariable("bookId") int bookId) {
          var resp =  commentService.getAllComments(bookId);
          return ApiResponse.<List<CommentResponse>>builder()
                  .result(resp)
                  .build();
     }
     @PostMapping("/{bookId}")
     public ApiResponse<CommentResponse> createComment(@PathVariable("bookId") int bookId, @RequestBody CommentRequest request) {
          var resp = commentService.createComment(bookId, request);
          return ApiResponse.<CommentResponse>builder().result(resp).build();
     }
     @PutMapping("/{commentId}")
     public ApiResponse<CommentResponse> updateComment(@PathVariable("commentId") int commentId, @RequestBody CommentRequest request) {
          var resp = commentService.updateComment(commentId, request);
          return ApiResponse.<CommentResponse>builder().result(resp).build();
     }

}
