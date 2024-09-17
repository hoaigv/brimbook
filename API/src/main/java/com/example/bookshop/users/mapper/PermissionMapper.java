package com.example.bookshop.users.mapper;

import com.example.bookshop.users.controllers.dto.permissions.PermissionRequest;
import com.example.bookshop.users.controllers.dto.permissions.PermissionResponse;
import com.example.bookshop.users.models.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
   PermissionEntity toPermissionEntity(PermissionRequest permissionRequest);
  PermissionResponse toPermissionResponse(PermissionEntity permissionEntity);
}
