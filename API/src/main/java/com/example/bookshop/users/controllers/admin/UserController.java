package com.example.bookshop.users.controllers.admin;

import com.example.bookshop.exceptionHandlers.CustomRunTimeException;
import com.example.bookshop.exceptionHandlers.ErrorCode;
import com.example.bookshop.users.controllers.dto.users.AdminUpdateUserRequest;
import com.example.bookshop.users.controllers.dto.users.UserCreationRequest;
import com.example.bookshop.users.controllers.dto.users.UserUpdateRequest;
import com.example.bookshop.users.models.UserEntity;
import com.example.bookshop.utils.ApiResponse;
import com.example.bookshop.users.controllers.dto.users.UserResponse;
import com.example.bookshop.users.services.IUserService;
import com.example.bookshop.utils.SortUtils;
import com.example.bookshop.utils.componentUtils.spec.UsersSpecification;
import com.example.bookshop.utils.validators.SortList;
import com.example.bookshop.utils.validators.ValidImage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNumeric;

@RestController("AdminUserController")
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    IUserService userService;

    private final static String DEFAULT_FILTER_PAGE = "0";
    private final static String DEFAULT_FILTER_SiZE = "10";
    private final static Sort DEFAULT_FILTER_SORT = Sort.by(Sort.Direction.DESC, "createdAt");

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserEntity>>> getUsers(
            UsersSpecification userSpecification,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_PAGE) String page,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_SiZE) String size,
            @RequestParam(required = false) String sortParam
    ) {
        if (!isNumeric(page) || !isNumeric(size)) {
            var resp = ApiResponse.<List<UserEntity>>builder()
                    .result(null)
                    .message("Page and size must be numeric")
                    .code(400)
                    .build();
            return ResponseEntity.badRequest().body(resp);
        }
        Sort sort = DEFAULT_FILTER_SORT;
        if (sortParam != null) {
            sort = SortUtils.parseSortParam(sortParam, Arrays.stream(SortList.USER_SORT_LIST).toList());
        }
        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), sort);
        var result = userService.getAllUsers(pageable, userSpecification);
        var resp = ApiResponse.<List<UserEntity>>builder()
                .result(result.getContent())
                .totalResults(result.getTotalElements())
                .page(result.getNumber() + 1)
                .pageSize(result.getSize())
                .totalPages(result.getTotalPages())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @RequestBody @Valid UserCreationRequest request) {

        var createUser = userService.createUser(request);
        var resp = ApiResponse.<UserResponse>builder()
                .result(createUser)
                .message("Successfully created user")
                .code(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PutMapping(value = "/{userId}" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<Void>> updateUserByAdmin(
            @RequestPart("data")  @Valid String data,
            @RequestPart(value = "image",required = false) MultipartFile image,
            @PathVariable Integer userId) {
        String fileName = null;
        if(image != null){
            fileName = image.getOriginalFilename();
        }
        if (fileName != null && !(fileName.endsWith(".jpg") || fileName.endsWith(".png"))) {
            return ResponseEntity.badRequest().body(ApiResponse.<Void>builder().code(400).message("Invalid file format. Only .jpg or .png are allowed.").build());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        AdminUpdateUserRequest request = null;
        try {
            request = objectMapper.readValue(data, AdminUpdateUserRequest.class);
        } catch (JsonProcessingException e) {
            var errorMessage = e.getMessage();
            int index = errorMessage.indexOf("(");
            errorMessage = errorMessage.substring(0, index);
        return   ResponseEntity.<Void>badRequest().body(ApiResponse.<Void>builder().code(400).message(errorMessage).build());
        }
        Set<ConstraintViolation<AdminUpdateUserRequest>> violations = Validation.buildDefaultValidatorFactory()
                .getValidator().validate(request);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest()
                    .body(ApiResponse.<Void>builder().code(400).message(errorMessage).build());
        }
        userService.adminUpdateUser(image,request,userId);
        var resp = ApiResponse.<Void>builder()
                .message("Successfully updated user")
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable("userId") int userId) {
        var resp = ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .message("Successfully get user by user_id")
                .code(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }
}
