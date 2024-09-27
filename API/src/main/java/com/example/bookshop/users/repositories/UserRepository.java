package com.example.bookshop.users.repositories;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.users.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    @Query("SELECT b FROM books b " +
            "JOIN likes l ON l.book.id = b.id " +
            "JOIN users u ON u.id = l.user.id " +
            "WHERE u.username = :username")
    Page<BookEntity> findLikedBooksByUsername(@Param("username") String username , Pageable pageable);
    @Query("SELECT b FROM books b " +
            "JOIN read_books r ON r.book.id = b.id " +
            "JOIN users u ON u.id = r.user.id " +
            "WHERE u.username = :username")
    Page<BookEntity> findReadBooksByUsername(@Param("username") String username , Pageable pageable);
}

