package com.example.bookshop.utils.baseEntities;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;

public class BaseWithCreatedByEntity extends BaseEntity {
    @Column
    @CreatedBy
    @NotNull(message = "createdBy must not be null")
    String createdBy;
}
