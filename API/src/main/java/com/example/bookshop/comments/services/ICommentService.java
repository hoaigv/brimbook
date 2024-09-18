package com.example.bookshop.comments.services;

import com.example.bookshop.comments.controller.dto.CommentRequest;
import com.example.bookshop.comments.controller.dto.CommentResponse;

import java.util.List;

public interface ICommentService {
    CommentResponse createComment(Integer bookId, CommentRequest request );
    CommentResponse updateComment(Integer commentId, CommentRequest request);
    List<CommentResponse> getAllComments(Integer bookId);
}
