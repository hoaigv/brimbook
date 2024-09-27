package com.example.bookshop.users.controllers.user;

import com.example.bookshop.users.controllers.dto.users.UserUpdateRequest;
import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.users.controllers.dto.users.UserCreationRequest;
import com.example.bookshop.users.controllers.dto.users.UserResponse;

import com.example.bookshop.users.services.IUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.stream.Collectors;

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


    @PutMapping(value = "/update" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> updateUser(
            @RequestPart(value = "data")  String data,
            @RequestPart(value = "image",required = false) MultipartFile image) {
        String fileName = null;
        if(image != null){
            fileName = image.getOriginalFilename();
        }
        if (fileName != null && !(fileName.endsWith(".jpg") || fileName.endsWith(".png")||fileName.endsWith(".PNG")||fileName.endsWith(".JPG")||fileName.endsWith(".webp"))) {
                return ResponseEntity.badRequest().body(ApiResponse.<Void>builder().code(400).message("Invalid file format. Only JPG or PNG are allowed.").build());
            }
            ObjectMapper objectMapper = new ObjectMapper();
        UserUpdateRequest request = null;
        try {
            request = objectMapper.readValue(data, UserUpdateRequest.class);
        } catch (JsonProcessingException e) {
            var errorMessage = e.getMessage();
            int index = errorMessage.indexOf("(");
            errorMessage = errorMessage.substring(0, index);
            return   ResponseEntity.<Void>badRequest().body(ApiResponse.<Void>builder().code(400).message(errorMessage).build());
        }
        Set<ConstraintViolation<UserUpdateRequest>> violations = Validation.buildDefaultValidatorFactory()
                    .getValidator().validate(request);
            if (!violations.isEmpty()) {
                String errorMessage = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                return ResponseEntity.badRequest()
                        .body(ApiResponse.<Void>builder().code(400).message(errorMessage).build());
            }
            userService.updateUser(request, image);
            var resp = ApiResponse.<Void>builder()
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

    @PostMapping("/like/{bookId}")
    public ResponseEntity<ApiResponse<Void>> likeUser(@PathVariable Integer bookId) {
        try {
            userService.likeBook(bookId);
            var resp = ApiResponse.<Void>builder()
                    .message("Đã thêm sách vào danh sách yêu thích thành công")
                    .code(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            log.error("Lỗi khi thêm sách vào danh sách yêu thích: ", e);
            var errorResp = ApiResponse.<Void>builder()
                    .message("Không thể thêm sách vào danh sách yêu thích: " + e.getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResp);
        }
    }

    @DeleteMapping("/like/{bookId}")
    public ResponseEntity<ApiResponse<Void>> unlikeUser(@PathVariable Integer bookId) {
        userService.unLikeBook(bookId);
        var resp = ApiResponse.<Void>builder().code(HttpStatus.OK.value()).build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }


}
