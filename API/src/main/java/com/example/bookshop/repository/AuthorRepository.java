package com.example.bookshop.repository;

import com.example.bookshop.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity,Integer> {
    AuthorEntity findByAuthorName(String name);

}
