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

    @NotNull(message = "category name  must not be null")
<<<<<<< HEAD
    String name;
=======
    @Column(unique = true)
    String  name;
>>>>>>> 38ed30754afb63e5e553e545501c1fdd5730b868

    @OneToMany(mappedBy = "category" , fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<BookEntity> books = new HashSet<>();

}
