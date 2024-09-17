package com.example.bookshop.dto.role;
import com.example.bookshop.dto.permission.PermissionResponse;
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
