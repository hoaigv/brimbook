package com.example.bookshop.users.controllers.dto.users;

import java.sql.Date;

import com.example.bookshop.utils.enums.Gender;
import com.example.bookshop.utils.enums.Role;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseBook {
    int id;

    String username;

    String email;

    String phone;

    String firstName;

    String lastName;

    String image_url;

    Date birthDate;

    Gender gender;

    Role role;

    
}
