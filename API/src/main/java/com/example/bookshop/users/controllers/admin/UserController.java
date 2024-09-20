package com.example.bookshop.users.controllers.admin;

import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.users.controllers.dto.users.UserResponse;
import com.example.bookshop.users.services.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("AdminUserController")
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    IUserService userService;

    private  final  static String DEFAULT_FILTER_LIMIT = "20";
    private  final  static   String DEFAULT_FILTER_OFFSET = "0";
    private  final  static Sort DEFAULT_FILTER_SORT = Sort.by(Sort.Direction.DESC , "createDate");
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers(
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT  ) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset
    ) {
        Pageable pageable = PageRequest.of(offset, limit, DEFAULT_FILTER_SORT);
        var resp = ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers(pageable))
                .build();

        return ResponseEntity.ok(resp);
    }
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createUser(){
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@RequestBody  List<String> roles , @RequestBody Integer userId){
      var resp =  ApiResponse.<UserResponse>builder().result( userService.updateRoleUser(roles,userId)).build();

      return ResponseEntity.ok(resp);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable("userId") int userId) {
       var resp = ApiResponse.<UserResponse>builder()
               .result(userService.getUserById(userId))
               .build();
        return ResponseEntity.ok(resp);
    }


}
