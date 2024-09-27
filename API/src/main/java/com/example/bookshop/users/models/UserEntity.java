package com.example.bookshop.users.models;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.comments.models.CommentEntity;
import com.example.bookshop.comments.models.RateEntity;
import com.example.bookshop.utils.baseEntities.BaseWithUpdatedByEntity;
import com.example.bookshop.utils.enums.Gender;
import com.example.bookshop.utils.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Table
@Entity(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserEntity extends BaseWithUpdatedByEntity {

    @Column(unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    @NotNull(message = "userName must not be null")
    String username;

    @NotNull(message = "password  must not be null")
    String password;

    @Column(unique = true)
    String email;

    String phone;

    @Lob
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "image url not null ")
    String image_url;

    String firstName;

    String lastName;

    Date birthDate;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @NotNull(message = "user role must be not null")
    @Enumerated(EnumType.STRING)
    Role role;

    @OneToMany(mappedBy = "user" ,fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    Set<LikeEntity> likes = new HashSet<>();

    @OneToMany(mappedBy = "user" ,fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    Set<ReadBooksEntity> read = new HashSet<>();

    @OneToMany(mappedBy = "user" ,fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    Set<CommentEntity> comments = new HashSet<>();

    @OneToMany(mappedBy = "user" ,fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    Set<RateEntity> rates = new HashSet<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    Set<BookEntity> books = new HashSet<>();

}
