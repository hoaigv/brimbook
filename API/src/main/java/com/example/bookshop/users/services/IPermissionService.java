package com.example.bookshop.users.services;

import com.example.bookshop.users.controllers.dto.permissions.PermissionRequest;
import com.example.bookshop.users.controllers.dto.permissions.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    void delete(String permission);
}
