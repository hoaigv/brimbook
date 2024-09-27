package com.example.bookshop.books.repositories;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.categories.models.CategoryEntity;
import com.example.bookshop.users.models.LikeEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> , JpaSpecificationExecutor<BookEntity>{
    @Query(value = "SELECT b FROM books b WHERE b.user.id = :userId")
    List<BookEntity> findAllByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT b FROM books b WHERE b.user.id =:userId AND b.id =:bookId")
    Optional<BookEntity> findByUserIdAndBookId(@Param("userId") Integer userId,@Param("bookId") Integer bookId);


    @Query(value = "SELECT b.* FROM books b " +
            "LEFT JOIN likes l ON b.id = l.book_id " +
            "GROUP BY b.id " +
            "ORDER BY COUNT(l.id) DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<BookEntity> findTopBooksByLikes();

    Page<BookEntity> findAllByLikes(Pageable pageable , List<LikeEntity> likes);


}
