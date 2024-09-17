package com.example.bookshop.users.controllers.dto.roles;
import com.example.bookshop.users.controllers.dto.permissions.PermissionResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    String roleName;
    String roleDescription;
    Set<PermissionResponse> permissions ;
}
