package com.example.bookshop.users.controllers.user;

import com.example.bookshop.users.controllers.dto.users.UserUpdateRequest;
import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.users.controllers.dto.users.UserCreationRequest;
import com.example.bookshop.users.controllers.dto.users.UserResponse;

import com.example.bookshop.users.services.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("WebUserController")
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    IUserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @RequestBody @Valid UserCreationRequest user) {

        var createUser = userService.createUser(user);
        var resp = ApiResponse.<UserResponse>builder()
                .result(createUser)
                .message("Successfully created user")
                .code(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }


    @PutMapping(value = "/upload")
    public ResponseEntity<ApiResponse<Void>> updateUser(
            @RequestPart("user") UserUpdateRequest request,
            @RequestPart("image") MultipartFile image) {
        try {
            String fileName = image.getOriginalFilename();
            if (fileName != null && !(fileName.endsWith(".jpg") || fileName.endsWith(".png"))) {
                return ResponseEntity.badRequest().body(ApiResponse.<Void>builder().code(400).message("Invalid file format. Only JPG or PNG are allowed.").build());
            }
            userService.updateUser(request, image);
            var resp = ApiResponse.<Void>builder()
                    .message("Successfully updated user")
                    .code(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.<Void>builder()
                    .code(400).message("Invalid user data").build());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo() {
        var resp = ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PostMapping("/like/{bookId}")
    public ResponseEntity<ApiResponse<Void>> likeUser(@PathVariable Integer bookId) {
        userService.likeBook(bookId);
        var resp = ApiResponse.<Void>builder().code(HttpStatus.OK.value()).build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @DeleteMapping("/like/{bookId}")
    public ResponseEntity<ApiResponse<Void>> unlikeUser(@PathVariable Integer bookId) {
        userService.unLikeBook(bookId);
        var resp = ApiResponse.<Void>builder().code(HttpStatus.OK.value()).build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }


}
