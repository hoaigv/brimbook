package com.example.bookshop.books.services;

import com.example.bookshop.books.controllers.dto.comments.CommentRequest;
import com.example.bookshop.books.controllers.dto.comments.CommentResponse;

import java.util.List;

public interface ICommentService {
    CommentResponse createComment(Integer bookId, CommentRequest request );
    CommentResponse updateComment(Integer commentId, CommentRequest request);
    List<CommentResponse> getAllComments(Integer bookId);
}
