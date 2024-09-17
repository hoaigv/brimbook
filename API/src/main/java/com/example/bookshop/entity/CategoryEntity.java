package com.example.bookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
public class CategoryEntity {
    @Id
    @NotNull(message = "code must not be null")
    String categoryCode;

    @NotNull(message = "category name  must not be null")
    String categoryName;

    @NotNull(message = "description must not be null")
    String categoryDescription;

    @ManyToMany(mappedBy = "categories")
    @JsonBackReference
    Set<BookEntity> books = new HashSet<>();

}
