package com.example.bookshop.users.controllers.admin;

import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.users.controllers.dto.roles.RoleRequest;
import com.example.bookshop.users.controllers.dto.roles.RoleResponse;
import com.example.bookshop.users.services.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    IRoleService roleService;
    @PostMapping
   public ResponseEntity<ApiResponse<RoleResponse>> create(@RequestBody RoleRequest roleRequest) {
      var resp = ApiResponse.<RoleResponse>builder()
              .result(roleService.create(roleRequest))
              .build();
        return  ResponseEntity.ok(resp);
    }
    @GetMapping
   public  ResponseEntity<ApiResponse<List<RoleResponse>>> getAll() {
        var resp = ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .build();
        return ResponseEntity.ok(resp);
    }
    @DeleteMapping("/{role}")
   public ResponseEntity< ApiResponse<Void> > delete(@PathVariable String role) {
        roleService.deleteRole(role);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update")
   public  ResponseEntity<ApiResponse<Void>> update(@RequestBody RoleRequest roleRequest) {
        roleService.update(roleRequest);
        return ResponseEntity.ok().build();
    }
}
