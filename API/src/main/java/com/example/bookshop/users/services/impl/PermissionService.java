package com.example.bookshop.users.services.impl;

import com.example.bookshop.users.controllers.dto.permissions.PermissionRequest;
import com.example.bookshop.users.controllers.dto.permissions.PermissionResponse;
import com.example.bookshop.users.models.PermissionEntity;
import com.example.bookshop.users.mapper.PermissionMapper;
import com.example.bookshop.users.repositories.PermissionRepository;
import com.example.bookshop.users.services.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService implements IPermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        PermissionEntity permission = permissionMapper.toPermissionEntity(request);
       var resp =permissionRepository.save(permission);
           return permissionMapper.toPermissionResponse(resp);
     }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();
     return   permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }
    @Override
    public void  delete(String permission) {

        permissionRepository.deleteById(permission);
    }


}
