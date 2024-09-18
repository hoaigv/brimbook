package com.example.bookshop.users.repositories;

import com.example.bookshop.users.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    List<UserEntity> findAllByBooksId(Integer books_id);
}
