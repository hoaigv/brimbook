package com.example.bookshop.users.repositories;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.users.models.ReadBooksEntity;
import com.example.bookshop.users.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ReadBookRepository extends JpaRepository<ReadBooksEntity, Integer> {
    Optional<ReadBooksEntity> findByBookAndUser(BookEntity book, UserEntity user);
}
