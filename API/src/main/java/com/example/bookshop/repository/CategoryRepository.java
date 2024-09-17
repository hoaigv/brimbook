package com.example.bookshop.repository;

import com.example.bookshop.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    CategoryEntity findByCategoryCode(String code);
    Set<CategoryEntity> findAllByCategoryCodeIn(Set<String> codes);
}
