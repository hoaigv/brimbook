package com.example.bookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chapters")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChapterEntity extends BaseWithCreatedByEntity {
    @NotNull(message = "title chapter must not be null")
    String chapterTitle;
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    @NotNull(message = "content must not be null")
    String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_book")
    @JsonBackReference
    @NotNull
    BookEntity book;

    @ManyToMany(mappedBy = "chapters")
    @JsonBackReference
    @NotNull
    Set<UserEntity> users = new HashSet<>();


}
