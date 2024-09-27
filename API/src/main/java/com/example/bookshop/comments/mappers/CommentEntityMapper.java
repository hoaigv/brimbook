package com.example.bookshop.comments.mappers;

import com.example.bookshop.books.controllers.dto.books.BookResponse;
import com.example.bookshop.comments.controllers.dto.CommentResponse;
import com.example.bookshop.comments.models.CommentEntity;
import com.example.bookshop.users.controllers.dto.users.UserResponseBook;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public class CommentEntityMapper {
    public static CommentResponse toCommentDTO(CommentEntity comment) {
        try {
            CommentResponse commentDTO = new CommentResponse();
            commentDTO.setId(comment.getId());
            commentDTO.setCommentText(comment.getCommentText());
            commentDTO.setIsVisible(comment.getIsVisible());

            BookResponse bookDTO = new BookResponse();
            bookDTO.setId(comment.getBook().getId());
            bookDTO.setTitle(comment.getBook().getTitle());
            bookDTO.setDescription(comment.getBook().getDescription());
            bookDTO.setImage_url(comment.getBook().getImage_url());
            bookDTO.setPublishedDate(comment.getBook().getPublishedDate());
            commentDTO.setBook(bookDTO);

            UserResponseBook userDTO = new UserResponseBook();
            userDTO.setId(comment.getUser().getId());
            userDTO.setFirstName(comment.getUser().getFirstName());
            userDTO.setLastName(comment.getUser().getLastName());
            userDTO.setEmail(comment.getUser().getEmail());
            userDTO.setPhone(comment.getUser().getPhone());
            userDTO.setUsername(comment.getUser().getUsername());
            userDTO.setImage_url(comment.getUser().getImage_url());
            userDTO.setGender(comment.getUser().getGender());
            userDTO.setRole(comment.getUser().getRole());
            userDTO.setBirthDate(comment.getUser().getBirthDate());
            commentDTO.setUser(userDTO);
            return commentDTO;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<CommentResponse> toCommentsDTOList(List<CommentEntity> comments) {
        return comments.stream()
                .map(CommentEntityMapper::toCommentDTO)
                .collect(Collectors.toList());
    }
}
