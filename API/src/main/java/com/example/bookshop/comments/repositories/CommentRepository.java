package com.example.bookshop.comments.repositories;

import com.example.bookshop.comments.models.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    Optional<List<CommentEntity>> findAllByBookId(Integer bookId);
}
