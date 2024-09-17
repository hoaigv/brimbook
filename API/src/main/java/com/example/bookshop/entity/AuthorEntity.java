package com.example.bookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "authors")
@Table
public class AuthorEntity  extends BaseWithCreatedByEntity{

    @NotNull(message = "author name must not be null")
    String authorName;
    @Lob
    @Column(columnDefinition = "TEXT")
    String biography;

    @ManyToMany(mappedBy = "authors")
    @JsonBackReference
    Set<BookEntity> books = new HashSet<>();

}
