package com.example.bookshop.users.controllers.dto.users;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminUpdateUserRequest extends UserUpdateRequest{
    @NotNull(message = "Image URL must not be null")
    @Pattern(regexp = "^(https?://.*\\.(jpg|png))$", message = "Image URL must end with .jpg or .png")
    String image_url;

    @Pattern(regexp = "ADMIN|USER", message = "Role must be either ADMIN or USER")
    @NotNull(message = "user role must be not null")
    String role;
}
