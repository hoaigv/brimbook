package com.example.bookshop.users.services.impl;

import com.example.bookshop.users.controllers.dto.users.UserUpdateRoleRequest;
import com.example.bookshop.users.controllers.dto.users.UserCreationRequest;
import com.example.bookshop.users.controllers.dto.users.UserUpdateRequest;
import com.example.bookshop.users.controllers.dto.users.UserResponse;
import com.example.bookshop.users.models.UserEntity;
import com.example.bookshop.exceptionHandlers.CustomRunTimeException;
import com.example.bookshop.exceptionHandlers.ErrorCode;
import com.example.bookshop.users.mappers.UserMapper;
import com.example.bookshop.users.repositories.UserRepository;
import com.example.bookshop.users.services.IUserService;
import com.example.bookshop.utils.AuthUtils;
import com.example.bookshop.utils.CloudUtils;
import com.example.bookshop.utils.enums.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService implements IUserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    CloudUtils cloudinary;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Integer id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.userToUserResponse(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers(Pageable pageable) {
        log.info("In method getAllUsers");
        var users = userRepository.findAll(pageable).getContent();

        return users.stream().map(userMapper::userToUserResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getMyInfo() {
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent())
                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        return userMapper.userToUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomRunTimeException(ErrorCode.USER_EXISTED);
        }
        UserEntity user = userMapper.userToUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setImage_url("https://res.cloudinary.com/dh4tdxre1/image/upload/v1726540809/cchxb6qoz2y89gvr9iol.jpg");
        try {
            userRepository.save(user);

        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException(exception.getMessage());
        }
        return userMapper.userToUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserUpdateRequest request) {
        UserEntity oldUser = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        var user = userMapper.updateUserEntity(oldUser, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    public void updateUserImage(MultipartFile image) {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("File have not data");
        }
        UserEntity oldUser = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        var link = cloudinary.uploadFile(image);
        oldUser.setImage_url(link);
        userRepository.save(oldUser);
    }

    @Override
    @Transactional
    public void delete(Set<Integer> ids) {
        log.info("In method delete");
        var oldUsers = userRepository.findAllById(ids);
        userRepository.deleteAll(oldUsers);
    }
//    @Override
//    @Transactional
//    public String addFavouriteBook(Integer id, String token) {
//        var bookEntity = bookRepository.findById(id).orElseThrow(
//                () -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND)
//        );
//        try {
//            FirebaseMessaging.getInstance().subscribeToTopic(Collections.singletonList(token), String.valueOf(bookEntity.getId()));
//        } catch (FirebaseMessagingException e) {
//            throw new CustomRunTimeException(ErrorCode.ADD_FAV_NOT_SUCCESS);
//        }
//        var user = userRepository.findByUsername(AuthUtils.getUserCurrent())
//                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
//        user.getBooks().add(bookEntity);
//        userRepository.save(user);
//        return bookEntity.getTitle();
//    }
//
//
//    @Override
//    @Transactional
//    public void addReadChapter(Integer id) {
//        var chapter = chapterRepository.findById(id).orElseThrow(
//                () -> new CustomRunTimeException(ErrorCode.CHAPTER_NOT_FOUND)
//        );
//        var user = userRepository.findByUsername(AuthUtils.getUserCurrent())
//                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
//        user.getChapters().add(chapter);
//        userRepository.save(user);
//    }

    @Override
    @Transactional
    public void updateRoleUser(UserUpdateRoleRequest request) {
//        var roleSet = roleRepository.findByRoleName(role.getRole()).orElseThrow(
//                () -> new CustomRunTimeException(ErrorCode.ROLE_NOT_FOUND)
//        );
//        var user = userRepository.findById(userId).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
//        user.setRole(roleSet);
//        userRepository.save(user);
    }
}
