package com.example.bookshop.users.controllers.dto.users;

import com.example.bookshop.utils.validators.AgeConstraint;
import com.example.bookshop.utils.validators.DobConstraint;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 6 , message = "username length must be 6")
    @NotNull(message = "user name not null")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must not contain special characters or accented characters")
    String username;

    @Size(min = 6 ,message = "password length must be 6" )
    @NotNull(message = "password not null")
    String password;

    @Email(message = "email  invalid")
    @NotNull(message = "email not null")
    @Pattern(regexp = "^[^\\d].*", message = "Email not start with numbers")
    String email;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid phone number (size : 10 -> 15 and only include number)")
    String phone;
    @Pattern(regexp = "[a-zA-Z ]+", message = "First name should  only include letters")
    String firstName;
    @Pattern(regexp = "[a-zA-Z ]+", message = "Last name should  only include letters")
    String lastName;

    @AgeConstraint
    @Pattern(regexp = "\\d+", message = "Age must contain only digits")
    String age;

    @DobConstraint(min = 18,message = "your age must > 18")
    LocalDate birthDate;


    Boolean gender;

}
