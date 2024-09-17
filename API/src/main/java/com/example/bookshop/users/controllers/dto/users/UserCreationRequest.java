package com.example.bookshop.users.controllers.dto.users;

import com.example.bookshop.utils.validator.DobConstraint;
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
    @Size(min = 6 , message = "Minimum length must be 6")
    @NotNull(message = "user name not null")
    String username;

    @Size(min = 6 ,message = "Minimum length must be 6" )
    @NotNull(message = "password not null")
    String password;

    @Email(message = "email  invalid")
    @NotNull(message = "email not null")
    String email;

    @Pattern(regexp = "\\d{10,15}", message = "Invalid phone number")
    String phone;
    @Pattern(regexp = "[a-zA-Z ]+", message = "First name should not contain numbers or special characters")
    @NotNull(message = "firstName not null")
    String firstName;
    @Pattern(regexp = "[a-zA-Z ]+", message = "Last name should not contain numbers or special characters")
    @NotNull(message = "lastName not null")
    String lastName;

    Integer age;

     @DobConstraint(min = 18,message = "INVALID_DOB")
    LocalDate birthDate;

    @NotNull(message = "gender not null")
    boolean gender;

}
