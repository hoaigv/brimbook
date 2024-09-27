package com.example.bookshop.comments.models;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.users.models.UserEntity;
import com.example.bookshop.utils.baseEntities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Table
@Entity(name = "comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CommentEntity extends BaseEntity {

    @Lob
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "name must not be null")
    String commentText;

    @Column(columnDefinition = "boolean default true")
    Boolean isVisible = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonBackReference
    @NotNull(message = "user_id must be not null")
    UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonBackReference
    @NotNull(message = "book_id must be not null")
    BookEntity book;

}
