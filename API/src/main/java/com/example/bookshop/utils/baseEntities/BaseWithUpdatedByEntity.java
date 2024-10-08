package com.example.bookshop.utils.baseEntities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BaseWithUpdatedByEntity extends BaseEntity {
    @Column
    @LastModifiedBy
    String updatedBy;
}
