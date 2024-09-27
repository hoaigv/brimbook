package com.example.bookshop.users.services.impl;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.books.repositories.BookRepository;
import com.example.bookshop.users.controllers.dto.users.AdminUpdateUserRequest;
import com.example.bookshop.users.controllers.dto.users.UserCreationRequest;
import com.example.bookshop.users.controllers.dto.users.UserUpdateRequest;
import com.example.bookshop.users.controllers.dto.users.UserResponse;
import com.example.bookshop.users.models.LikeEntity;
import com.example.bookshop.users.models.ReadBooksEntity;
import com.example.bookshop.users.models.UserEntity;
import com.example.bookshop.exceptionHandlers.CustomRunTimeException;
import com.example.bookshop.exceptionHandlers.ErrorCode;
import com.example.bookshop.users.mappers.UserMapper;
import com.example.bookshop.users.repositories.LikeRepository;
import com.example.bookshop.users.repositories.ReadBookRepository;
import com.example.bookshop.users.repositories.UserRepository;
import com.example.bookshop.users.services.IUserService;
import com.example.bookshop.utils.AuthUtils;
import com.example.bookshop.utils.CloudUtils;
import com.example.bookshop.utils.componentUtils.spec.UsersSpecification;
import com.example.bookshop.utils.enums.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService implements IUserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    CloudUtils cloudinary;
    BookRepository bookRepository;
    LikeRepository likeRepository;
    ReadBookRepository readBookRepository;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Integer id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.userToUserResponse(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserEntity> getAllUsers(Pageable pageable, UsersSpecification specification) {
        // Đảm bảo rằng phương thức này đang sử dụng đúng pageable
        return (specification != null) 
        ? userRepository.findAll(specification, pageable) 
        : userRepository.findAll(pageable);
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

        user.setImage_url("https://stcv4.hnammobile.com/downloads/a/cach-chup-anh-selfie-dep-an-tuong-ban-nhat-dinh-phai-biet-81675319567.jpg");
        try {
            userRepository.save(user);

        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException(exception.getMessage());
        }
        return userMapper.userToUserResponse(user);
    }

    @Override
    @Transactional
    public void updateUser(UserUpdateRequest request, MultipartFile image) {
        UserEntity oldUser = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
         userMapper.updateUserEntity(oldUser, request);
        oldUser.setPassword(oldUser.getPassword());
        if (image!= null && !image.isEmpty()) {
            cloudinary.deleteFileAsync(oldUser.getImage_url());
            String link ;
            try {
                link = cloudinary.uploadFileAsync(image).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new CustomRunTimeException(ErrorCode.SET_IMAGE_NOT_SUCCESS);
            }
            oldUser.setImage_url(link);
        }

        userRepository.save(oldUser);
    }

    @Override
    @Transactional
    public void adminUpdateUser(MultipartFile image, AdminUpdateUserRequest request, Integer userId) {
        var oldUser = userRepository.findById(userId).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUserEntityByAdmin(oldUser, request);
        oldUser.setPassword(oldUser.getPassword());
        if (image != null) {
            cloudinary.deleteFileAsync(oldUser.getImage_url());
            String link ;
            try {
                link = cloudinary.uploadFileAsync(image).get();
            } catch (InterruptedException | ExecutionException e) {
                throw new CustomRunTimeException(ErrorCode.SET_IMAGE_NOT_SUCCESS);
            }
            oldUser.setImage_url(link);
        }
        userRepository.save(oldUser);
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        
        // Xóa ảnh của người dùng trên Cloudinary (nếu có)
        if (user.getImage_url() != null && !user.getImage_url().isEmpty()) {
            cloudinary.deleteFileAsync(user.getImage_url());
        }
        
        // Xóa người dùng khỏi cơ sở dữ liệu
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void likeBook(Integer bookId) {
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        var book = bookRepository.findById(bookId).orElseThrow(() -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND));
        if(likeRepository.existsByBookAndUser(book,user)){
            throw new  CustomRunTimeException(ErrorCode.LIKE_EXISTED);
        }
        var likeEntity = LikeEntity.builder()
        .book(book)
        .user(user)
        .build();
        likeRepository.save(likeEntity);
    }

    @Override
    @Transactional
    public void unLikeBook(Integer bookId){
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        var book = bookRepository.findById(bookId).orElseThrow(() -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND));
        var like = likeRepository.findByBookAndUser(book,user).orElseThrow(()-> new CustomRunTimeException(ErrorCode.LIKE_NOT_FOUND));
        likeRepository.delete(like);
    }

    @Override
    public void delete(Set<Integer> ids) {
        userRepository.deleteAllById(ids);
        log.info("Đã xóa {} người dùng", ids.size());
    }
    

    public boolean getLikeBook(Integer bookId) {
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        var book = bookRepository.findById(bookId).orElseThrow(() -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND));
     return  likeRepository.existsByBookAndUser(book,user);
    }

    @Override
    public Page<BookEntity> getAllLikeBook(Pageable pageable) {
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        return userRepository.findLikedBooksByUsername(user.getUsername(), pageable);
    }

    @Override
    @Transactional
    public void readBook(Integer bookId) {
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        var book = bookRepository.findById(bookId).orElseThrow(() -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND));
        var readBook = ReadBooksEntity.builder().book(book).user(user).build();
        readBookRepository.save(readBook);
    }

    @Override
    @Transactional
    public void deleteReadBook(Integer bookId) {
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        var book = bookRepository.findById(bookId).orElseThrow(() -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND));
        var readBook = readBookRepository.findByBookAndUser(book,user).orElseThrow(()-> new CustomRunTimeException(ErrorCode.LIKE_NOT_FOUND));
        readBookRepository.delete(readBook);
    }

    @Override
    public Page<BookEntity> getAllReadBook(Pageable pageable) {
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        return userRepository.findReadBooksByUsername(user.getUsername(), pageable);
    }

}
