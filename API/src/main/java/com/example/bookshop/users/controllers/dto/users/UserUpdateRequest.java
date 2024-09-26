package com.example.bookshop.users.controllers.dto.users;

import com.example.bookshop.utils.validators.DobConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @Pattern(regexp="^[a-zA-Z][a-zA-Z0-9._-]*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format. Please enter a valid email (e.g., example@domain.com)")
    String email;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid phone number (size : 10 -> 15 and only include number)")

    String phone;
    @Pattern(regexp = "[a-zA-Z ]+", message = "First name should only include letters")
    String firstName;
    @Pattern(regexp = "[a-zA-Z ]+", message = "Last name should only include letters")
    String lastName;

    Date birthDate;


    @Pattern(regexp = "MALE|FEMALE", message = "Gender must be either MALE or FEMALE")
    String gender;

}
