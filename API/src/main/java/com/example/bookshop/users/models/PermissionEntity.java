package com.example.bookshop.users.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "permissions")
@Table
public class PermissionEntity {
    @Id
    @Column(unique = true )
    @NotNull(message = "Permission must be not null")
    @NotEmpty(message = "Permission name must not be empty")
    @Length(min = 5, max = 20, message = "name  length must be between 5 and 20 characters")
    @Pattern(regexp = "\\S+", message = "Permission name must not contain spaces")
    String permissionName;

    @NotNull(message = "Permission must be not null")
    String permissionDesc;

    @ManyToMany(mappedBy = "permissions")
    Set<RoleEntity> roles = new HashSet<>();


}
