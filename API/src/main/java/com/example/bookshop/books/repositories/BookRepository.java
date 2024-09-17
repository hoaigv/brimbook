package com.example.bookshop.books.repositories;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.books.models.CategoryEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> , JpaSpecificationExecutor<BookEntity> {
    interface Specs{
        static Specification<BookEntity> byCategories(Set<CategoryEntity> categories){
            return ((root, query, criteriaBuilder) -> {
                Predicate predicate = criteriaBuilder.conjunction();
                for(CategoryEntity category : categories){
                    predicate = criteriaBuilder.and(predicate, criteriaBuilder.isMember(category,root.get("categories")));
                }
                return predicate;
            });
        }

    }
}
