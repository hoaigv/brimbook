package com.example.bookshop.categories.models;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.utils.baseEntities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "categories")
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryEntity extends BaseEntity {
    @Id
    @NotNull(message = "code must not be null")
    Integer id;

    @NotNull(message = "category name  must not be null")
    String name;

    @OneToMany(mappedBy = "category" , fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<BookEntity> books = new HashSet<>();

}
