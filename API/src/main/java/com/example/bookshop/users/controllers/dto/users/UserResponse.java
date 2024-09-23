package com.example.bookshop.users.controllers.dto.users;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

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


    Date birthDate;

    Boolean gender;

    String image_url;;


    String role;
}
