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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("WebUserController")
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    IUserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(
            @RequestPart("data") @Valid  UserCreationRequest userCreationRequest,
            @RequestPart ("image") MultipartFile image) {
        var resp = userService.createUser(userCreationRequest,image);
        return ApiResponse.<UserResponse>builder()
                .result(resp )
                .build();
    }
    @PutMapping
   public  ApiResponse<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(request))
                .build();
    }
    @GetMapping
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
    @PutMapping("/favorite/{bookId}")
    public ApiResponse<String> addFavorite(@PathVariable int bookId , @RequestBody String token) {
        var resp = userService.addFavouriteBook(bookId,token);
        return ApiResponse.<String>builder()
                .result(resp)
                .build();
    }

    @PutMapping("/read-chapter/{chapterId}")
    public ApiResponse<Void> addReadChapter(@PathVariable int chapterId) {
         userService.addReadChapter(chapterId);
        return ApiResponse.<Void>builder()
                .build();
    }


}
