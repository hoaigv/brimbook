package com.example.bookshop.users.controllers.dto.users;

import com.example.bookshop.utils.validators.AgeConstraint;
import com.example.bookshop.utils.validators.DobConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 6 , message = "username length must be 6")
    @NotNull(message = "user name not null")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must not contain special characters or accented characters")
    String username;

    @Size(min = 6 ,message = "password length must be 6")
    @NotNull(message = "password not null")
    String password;

    @NotNull(message = "email not null")
    @Pattern(regexp="^[a-zA-Z][a-zA-Z0-9._-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format. Please enter a valid email (e.g., example@domain.com)")
    String email;

}
