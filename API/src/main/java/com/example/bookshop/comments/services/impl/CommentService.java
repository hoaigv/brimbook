package com.example.bookshop.comments.services.impl;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.books.repositories.BookRepository;
import com.example.bookshop.comments.controllers.CommentWebSocketController;
import com.example.bookshop.comments.controllers.dto.CommentRequest;
import com.example.bookshop.comments.controllers.dto.CommentResponse;
import com.example.bookshop.comments.mappers.CommentEntityMapper;
import com.example.bookshop.comments.models.CommentEntity;
import com.example.bookshop.comments.repositories.CommentRepository;
import com.example.bookshop.comments.services.ICommentService;
import com.example.bookshop.exceptionHandlers.CustomRunTimeException;
import com.example.bookshop.exceptionHandlers.ErrorCode;
import com.example.bookshop.users.models.UserEntity;
import com.example.bookshop.users.repositories.UserRepository;
import com.example.bookshop.utils.AuthUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CommentService implements ICommentService {
    CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CommentWebSocketController commentWebSocketController;

    @Override
    public List<CommentResponse> getCommentsByIdBook(int bookId) {
        bookRepository.findById(bookId).orElseThrow(() -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND));
        var comments = commentRepository.findAllByBookId(bookId);
        return CommentEntityMapper.toCommentsDTOList(comments.get());
    }

    @Override
    public CommentResponse sendComment(CommentRequest commentRequest, Integer bookId) {
        System.out.println("Comment "+commentRequest);
        UserEntity user = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        BookEntity books = bookRepository.findById(bookId).orElseThrow(()->new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND));

        var comment = modelMapper.map(commentRequest, CommentEntity.class);
        comment.setUser(user);
        comment.setBook(books);
        var newCommment = commentRepository.save(comment);
        var map = modelMapper.map(newCommment, CommentResponse.class);
        commentWebSocketController.broadcastNewComment(map);
        return map;
    }
}
