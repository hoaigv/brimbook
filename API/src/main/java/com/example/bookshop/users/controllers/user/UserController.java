package com.example.bookshop.users.controllers.user;

import com.example.bookshop.users.controllers.dto.users.UserUpdateRequest;
import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.users.controllers.dto.users.UserCreationRequest;
import com.example.bookshop.users.controllers.dto.users.UserResponse;

import com.example.bookshop.users.services.IUserService;
import com.example.bookshop.utils.validators.ValidImage;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateUser(
            @RequestPart("user") @Valid UserUpdateRequest request,
             @RequestPart("image")  MultipartFile image) {
        String fileName = image.getOriginalFilename();
        if (fileName != null && !(fileName.endsWith(".jpg") || fileName.endsWith(".png"))) {
            return ResponseEntity.badRequest().body(ApiResponse.<Void>builder().code(400).message("Invalid file format. Only JPG or PNG are allowed.").build());
        }
        userService.updateUser(request , image);
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
