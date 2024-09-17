package com.example.bookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class BookEntity extends BaseWithCreatedByEntity {
    @NotNull(message = "publisher must not be null")
    String publisher;
    @NotNull(message = "title must not be null")
    String title;
    @Lob
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "description must not be null")
    String description;
    @Lob
    @Column(columnDefinition = "TEXT")
    String resume;
    @Lob
    @Column(columnDefinition = "TEXT")
    @NotNull(message = "reissue must not be null")
    LocalDateTime reissueNotes;

    @Lob
    @Column(columnDefinition = "TEXT")
    @Pattern(regexp = ".*\\.(png|jpg)$", message = "Image must have a valid extension (.png or .jpg)")
    @NotNull(message = "img not null")
    String image_url;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_authors", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonManagedReference
    Set<AuthorEntity> authors = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_categorys", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonManagedReference
    Set<CategoryEntity> categories = new HashSet<>();


    @OneToMany(mappedBy = "book")
    List<ChapterEntity> chapters = new ArrayList<>();

    @ManyToMany(mappedBy = "books")
    @JsonBackReference
    Set<UserEntity> users = new HashSet<>();

    @OneToMany(mappedBy = "book")
    @JsonBackReference
    List<CommentEntity> comments = new ArrayList<>();

}
