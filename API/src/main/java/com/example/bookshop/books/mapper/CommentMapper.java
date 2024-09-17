package com.example.bookshop.books.mapper;

import com.example.bookshop.books.controllers.dto.comments.CommentRequest;
import com.example.bookshop.books.controllers.dto.comments.CommentResponse;
import com.example.bookshop.books.models.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentEntity toCommentEntity(CommentRequest request);
    CommentResponse toCommentResponse(CommentEntity entity);
}
