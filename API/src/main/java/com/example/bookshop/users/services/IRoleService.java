package com.example.bookshop.users.services;

import com.example.bookshop.users.controllers.dto.roles.RoleRequest;
import com.example.bookshop.users.controllers.dto.roles.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse create(RoleRequest request);
    List<RoleResponse> getAllRoles();
    void deleteRole(String role);
    RoleResponse update(RoleRequest request);

}
