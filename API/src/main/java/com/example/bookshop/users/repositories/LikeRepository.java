package com.example.bookshop.users.repositories;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.users.models.LikeEntity;
import com.example.bookshop.users.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
  Optional<LikeEntity> findByBookAndUser(BookEntity book, UserEntity user);
  boolean existsByBookAndUser(BookEntity book, UserEntity user);

  @Query("SELECT COUNT(l) FROM likes l WHERE l.book.id = :bookId")
  Integer countLikesByBookId(@Param("bookId") Integer bookId);
}
