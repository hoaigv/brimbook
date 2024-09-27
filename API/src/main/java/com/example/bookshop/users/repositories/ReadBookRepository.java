package com.example.bookshop.users.repositories;


import com.example.bookshop.users.models.ReadBooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadBookRepository extends JpaRepository<ReadBooksEntity, Integer> {
    @Query("SELECT COUNT(l) FROM read_books l WHERE l.book.id = :bookId")
    Integer countReadBooksByBookId(@Param("bookId") Integer bookId);
}
