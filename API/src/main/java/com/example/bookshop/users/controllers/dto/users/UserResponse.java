package com.example.bookshop.users.controllers.dto.users;

import com.example.bookshop.utils.enums.Gender;
import com.example.bookshop.utils.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Integer id;

    String username;

    String password;

    String email;

    String phone;

    String firstName;

    String lastName;


    Date birthDate;

    Gender gender;

    String image_url;

    Role role;
}
