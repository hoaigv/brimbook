package com.example.bookshop.users.repositories;

<<<<<<< HEAD

import com.example.bookshop.users.models.ReadBooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadBookRepository extends JpaRepository<ReadBooksEntity, Integer> {
    @Query("SELECT COUNT(l) FROM read_books l WHERE l.book.id = :bookId")
    Integer countReadBooksByBookId(@Param("bookId") Integer bookId);
=======
import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.users.models.ReadBooksEntity;
import com.example.bookshop.users.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ReadBookRepository extends JpaRepository<ReadBooksEntity, Integer> {
    Optional<ReadBooksEntity> findByBookAndUser(BookEntity book, UserEntity user);
>>>>>>> 38ed30754afb63e5e553e545501c1fdd5730b868
}
