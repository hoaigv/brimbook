package com.example.bookshop.users.controllers.dto.users;

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
}
