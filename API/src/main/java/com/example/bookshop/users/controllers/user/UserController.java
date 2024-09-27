package com.example.bookshop.users.controllers.user;

import com.example.bookshop.books.controllers.dto.books.BookResponse;
import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.users.controllers.dto.users.UserUpdateRequest;
import com.example.bookshop.users.models.UserEntity;
import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.users.controllers.dto.users.UserCreationRequest;
import com.example.bookshop.users.controllers.dto.users.UserResponse;

import com.example.bookshop.users.services.IUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@RestController("WebUserController")
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    IUserService userService;
    private final static String DEFAULT_FILTER_PAGE = "0";
    private final static String DEFAULT_FILTER_SIZE = "10";
    private final static Sort DEFAULT_FILTER_SORT = Sort.by(Sort.Direction.DESC, "createdAt");



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
        if (fileName != null && !(fileName.endsWith(".jpg") || fileName.endsWith(".png")||fileName.endsWith(".PNG")||fileName.endsWith(".JPG"))) {
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
            userService.likeBook(bookId);
            var resp = ApiResponse.<Void>builder()
                    .message("Add book in like list successfully")
                    .code(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @DeleteMapping("/like/{bookId}")
    public ResponseEntity<ApiResponse<Void>> unlikeUser(@PathVariable Integer bookId) {
        userService.unLikeBook(bookId);
        var resp = ApiResponse.<Void>builder().code(HttpStatus.OK.value()).build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PostMapping("/read/{bookId}")
    public ResponseEntity<ApiResponse<Void>> readBook(@PathVariable Integer bookId) {
        userService.readBook(bookId);
        var resp = ApiResponse.<Void>builder().code(HttpStatus.OK.value()).build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
    @DeleteMapping("/read/{bookId}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Integer bookId) {
        userService.deleteReadBook(bookId);
        var resp = ApiResponse.<Void>builder().message("Delete history read book successfully !").code(HttpStatus.OK.value()).build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @GetMapping("/list-book-like")
    public ResponseEntity<ApiResponse<List<BookEntity>>> getListBookLike(
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_PAGE) String page,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_SIZE) String size

    ){
        if (!isNumeric(page) || !isNumeric(size)) {
            var resp = ApiResponse.<List<BookEntity>>builder()
                    .result(null)
                    .message("Page and size must be numeric")
                    .code(400)
                    .build();
            return ResponseEntity.badRequest().body(resp);
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), DEFAULT_FILTER_SORT);
        var result = userService.getAllLikeBook(pageable);
        var resp = ApiResponse.<List<BookEntity>>builder()
                .result(result.getContent())
                .totalResults(result.getTotalElements())
                .page(result.getNumber() + 1)
                .pageSize(result.getSize())
                .totalPages(result.getTotalPages())
                .build();
        return  ResponseEntity.status(HttpStatus.OK).body(resp);
    }
    @GetMapping("/list-book-read")
    public ResponseEntity<ApiResponse<List<BookEntity>>> getListBookRead(
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_PAGE) String page,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_SIZE) String size

    ){
        if (!isNumeric(page) || !isNumeric(size)) {
            var resp = ApiResponse.<List<BookEntity>>builder()
                    .result(null)
                    .message("Page and size must be numeric")
                    .code(400)
                    .build();
            return ResponseEntity.badRequest().body(resp);
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), DEFAULT_FILTER_SORT);
        var result = userService.getAllReadBook(pageable);
        var resp = ApiResponse.<List<BookEntity>>builder()
                .result(result.getContent())
                .totalResults(result.getTotalElements())
                .page(result.getNumber() + 1)
                .pageSize(result.getSize())
                .totalPages(result.getTotalPages())
                .build();
        return  ResponseEntity.status(HttpStatus.OK).body(resp);
    }

}
