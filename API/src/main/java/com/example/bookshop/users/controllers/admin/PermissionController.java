package com.example.bookshop.users.controllers.admin;

import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.users.controllers.dto.permissions.PermissionRequest;
import com.example.bookshop.users.controllers.dto.permissions.PermissionResponse;
import com.example.bookshop.users.services.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    IPermissionService permissionService;
    @PostMapping
    public ResponseEntity< ApiResponse<PermissionResponse>> create(@RequestBody PermissionRequest permissionRequest) {
       var resp =  ApiResponse.<PermissionResponse>builder()
               .result(permissionService.create(permissionRequest))
               .build();
        return ResponseEntity.ok(resp);
    }
    @GetMapping
   public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAll() {
        var resp = ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAllPermissions())
                .build();
        return ResponseEntity.ok(resp);
    }
    @DeleteMapping("/{permission}")
    public ApiResponse<Void> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return ApiResponse.<Void>builder().build();
    }
}
