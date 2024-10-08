package com.example.bookshop.utils.baseEntities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    @CreatedDate
    @NotNull(message = "created at must not be null")
    LocalDateTime createdAt;

    @Column
    @LastModifiedDate
    LocalDateTime updatedAt;


}
