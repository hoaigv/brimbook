package com.example.bookshop.books.models;

import com.example.bookshop.categories.models.CategoryEntity;
import com.example.bookshop.comments.models.CommentEntity;
import com.example.bookshop.comments.models.RateEntity;
import com.example.bookshop.users.models.LikeEntity;
import com.example.bookshop.users.models.ReadBooksEntity;
import com.example.bookshop.users.models.UserEntity;
import com.example.bookshop.utils.baseEntities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "books")
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookEntity extends BaseEntity {

    @NotNull(message = "title must not be null")
    String title;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    @NotNull(message = "description must not be null")
    String description;

    @Lob
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "img not null")
    String image_url;

    Date publishedDate;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<LikeEntity> likes = new HashSet<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<ReadBooksEntity> read = new HashSet<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @JsonBackReference
    Set<CommentEntity> comments = new HashSet<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @JsonBackReference
    Set<RateEntity> rates = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonBackReference
    @NotNull(message = "category must not be null")
    CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonBackReference
    @NotNull(message = "user_id must not be null")
    UserEntity user;

}
