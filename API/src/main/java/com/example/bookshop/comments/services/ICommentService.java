package com.example.bookshop.comments.services;

import com.example.bookshop.comments.controllers.dto.CommentRequest;
import com.example.bookshop.comments.controllers.dto.CommentResponse;

import java.util.List;

public interface ICommentService {
    List<CommentResponse> getCommentsByIdBook(int id);
    CommentResponse sendComment(CommentRequest commentRequest, Integer id);
}
