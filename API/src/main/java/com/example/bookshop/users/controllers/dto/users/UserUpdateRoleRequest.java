package com.example.bookshop.users.controllers.dto.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRoleRequest {
    @Pattern(regexp = "ADMIN|USER", message = "Role must be either ADMIN or USER")
    String role;

    @NotEmpty(message = "UserIds cannot be empty")
    @Valid
    List<@Positive(message = "User ID must be a positive number") Integer> userIds;
}
