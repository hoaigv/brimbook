package com.example.bookshop.comments.models;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.users.models.UserEntity;
import com.example.bookshop.utils.baseEntities.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Table
@Entity(name = "ratings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class RateEntity extends BaseEntity {
    @Min(1)
    @Max(5)
    @NotNull(message = "rate must be not null")
    Integer ratingValue;

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
