package com.example.bookshop.users.models;

import com.example.bookshop.books.models.BookEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Table
@Entity(name = "relationships")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRelationshipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    @CreatedDate
    @NotNull(message = "createdDate must not be null")
    LocalDateTime createdDate;

    @Column
    @NotNull(message = "relationship with boot mus not be null")
    String relationship;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonBackReference
    @NotNull(message = "user_id must not be null")
    UserEntity user ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonBackReference
    @NotNull(message = "book_id must not be null")
    BookEntity book;

}
