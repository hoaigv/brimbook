package com.example.bookshop.users.controllers.dto.users;

import com.example.bookshop.users.controllers.dto.roles.RoleResponse;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String username;

    String password;

    String email;

    String phone;

    String firstName;

    String lastName;

    Integer age;

    Date birthDate;

    Boolean gender;

    String image_url;;


    Set<RoleResponse> roles ;
}
