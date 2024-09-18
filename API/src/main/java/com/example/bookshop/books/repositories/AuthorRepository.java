package com.example.bookshop.books.repositories;

import com.example.bookshop.books.models.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity,Integer> {
    AuthorEntity findByAuthorName(String name);

}
