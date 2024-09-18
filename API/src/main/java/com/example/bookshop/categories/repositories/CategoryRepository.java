package com.example.bookshop.categories.repositories;

import com.example.bookshop.categories.models.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    CategoryEntity findByCategoryCode(String code);
    Set<CategoryEntity> findAllByCategoryCodeIn(Set<String> codes);
}
