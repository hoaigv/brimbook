package com.example.bookshop.users.controllers.user;

import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.users.controllers.dto.users.UserCreationRequest;
import com.example.bookshop.users.controllers.dto.users.UserResponse;

import com.example.bookshop.users.controllers.dto.users.UserUpdateRequest;
import com.example.bookshop.users.services.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController("WebUserController")
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    IUserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @RequestBody @Valid UserCreationRequest userCreationRequest) {
        var createUser = userService.createUser(userCreationRequest);
        var resp = ApiResponse.<UserResponse>builder()
                .result(createUser)
                .message("Successfully created user")
                .code(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        userService.updateUser(request);
        var resp = ApiResponse.<Void>builder()
                .result(null)
                .message("Successfully updated user")
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PutMapping("update/image")
    public ResponseEntity<ApiResponse<Void>> updateImageUser(@RequestPart("image") MultipartFile image) {
        String fileName = image.getOriginalFilename();
        if (image.isEmpty() || !Objects.requireNonNull(fileName).endsWith(".jpg") && !fileName.endsWith(".png")) {
            var resp = ApiResponse.<Void>builder()
                    .code(400)
                    .message("you need choose file img (.jpg or .png) ")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
        } else {
            userService.updateUserImage(image);
        }
        var resp = ApiResponse.<Void>builder()
                .result(null)
                .message("Successfully updated user")
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo() {
        var resp = ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

//    @PutMapping("/favorite/{bookId}")
//    public ResponseEntity<ApiResponse<Void>> addFavorite(@PathVariable int bookId, @RequestBody String token) {
//        userService.addFavouriteBook(bookId, token);
//        var resp = ApiResponse.<Void>builder()
//                .result(null)
//                .message("Successfully updated user")
//                .code(HttpStatus.OK.value())
//                .build();
//        return ResponseEntity.status(HttpStatus.OK).body(resp);
//    }

//    @PutMapping("/read-chapter/{chapterId}")
//    public ResponseEntity<ApiResponse<Void>> addReadChapter(@PathVariable int chapterId) {
//        userService.addReadChapter(chapterId);
//        var resp = ApiResponse.<Void>builder()
//                .result(null)
//                .message("Successfully updated user")
//                .code(HttpStatus.OK.value())
//                .build();
//        return ResponseEntity.status(HttpStatus.OK).body(resp);
//    }


}
