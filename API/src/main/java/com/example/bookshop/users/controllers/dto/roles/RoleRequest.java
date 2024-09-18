package com.example.bookshop.users.controllers.dto.roles;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    String roleName;
    String roleDescription;
    Set<String> permissions ;
}
