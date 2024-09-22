package com.example.bookshop.users.models;

import com.example.bookshop.comments.models.CommentEntity;
import com.example.bookshop.utils.baseEntities.BaseAllEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "roles")
@Table
public class RoleEntity extends BaseAllEntity {

    @NotNull
    @Column(unique = true)
    String roleName;

    @OneToMany(mappedBy = "role")
    @JsonBackReference
    Set<UserEntity> users = new HashSet<>();
}
