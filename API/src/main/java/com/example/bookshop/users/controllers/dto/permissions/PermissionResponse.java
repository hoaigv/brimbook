package com.example.bookshop.users.controllers.dto.permissions;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionResponse {
    String permissionName ;
    String permissionDesc;
}
