package com.example.bookshop.comments.mappers;

import com.example.bookshop.comments.controller.dto.CommentRequest;
import com.example.bookshop.comments.controller.dto.CommentResponse;
import com.example.bookshop.comments.models.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentEntity toCommentEntity(CommentRequest request);
    CommentResponse toCommentResponse(CommentEntity entity);
}
