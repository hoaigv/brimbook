package com.example.bookshop.books.repositories;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.categories.models.CategoryEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> , JpaSpecificationExecutor<BookEntity> {
    @Query(value = "SELECT b FROM books b WHERE b.user.id = :userId")
    List<BookEntity> findAllByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT b FROM books b WHERE b.user.id =:userId AND b.id =:bookId")
    Optional<BookEntity> findByUserIdAndBookId(@Param("userId") Integer userId,@Param("bookId") Integer bookId);
}
