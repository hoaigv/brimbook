package com.example.bookshop.users.models;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.books.models.ChapterEntity;
import com.example.bookshop.books.models.CommentEntity;
import com.example.bookshop.utils.baseEntities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table
@Entity(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserEntity extends BaseEntity {

    @Column(unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    @NotNull(message = "userName must not be null")
    String username;

    @NotNull(message = "password  must not be null")
    @Length(min = 5, max = 255, message = "Password length must be between 5 and 50 characters")
    String password;

    @Email(message = "Email should be valid")
    @Column(unique = true)
    String email;

    String phone;

    @Lob
    @Column(columnDefinition = "TEXT")
    @Pattern(regexp = ".*\\.(png|jpg)$", message = "Image must have a valid extension (.png or .jpg)")
    @NotNull(message = "image url not null ")
    String image_url;

    @NotNull(message = "firstName  must not be null")
    String firstName;

    @NotNull(message = "lastName  must not be null")
    String lastName;

    Integer age;

    Date birthDate;

    boolean gender;


    @NotNull(message = "status  must not be null")
    boolean status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    List<CommentEntity> comments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_chapters", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "chapter_id"))
    @JsonManagedReference
    Set<ChapterEntity> chapters = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_books", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    @JsonManagedReference
    Set<BookEntity> books = new HashSet<>();


}
