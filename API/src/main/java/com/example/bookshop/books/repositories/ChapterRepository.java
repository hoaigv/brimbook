package com.example.bookshop.books.repositories;

import com.example.bookshop.books.models.ChapterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ChapterRepository extends JpaRepository<ChapterEntity,Integer> {
    Set<ChapterEntity> findByBookId(Integer bookId);
}
