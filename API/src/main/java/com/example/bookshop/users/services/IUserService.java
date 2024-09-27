package com.example.bookshop.users.services;

import com.example.bookshop.books.models.BookEntity;
import com.example.bookshop.users.controllers.dto.users.AdminUpdateUserRequest;
import com.example.bookshop.users.controllers.dto.users.UserCreationRequest;
import com.example.bookshop.users.controllers.dto.users.UserUpdateRequest;
import com.example.bookshop.users.controllers.dto.users.UserResponse;
import com.example.bookshop.users.models.UserEntity;
import com.example.bookshop.utils.componentUtils.spec.UsersSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface IUserService {
    UserResponse getUserById(Integer id) ;
    Page<UserEntity> getAllUsers(Pageable pageable , UsersSpecification usersSpecification);
    Page<BookEntity> getAllLikeBook(Pageable pageable);
    Page<BookEntity> getAllReadBook(Pageable pageable);
    UserResponse getMyInfo();
    boolean getLikeBook(Integer bookId);

    UserResponse createUser(UserCreationRequest request );
    void updateUser(UserUpdateRequest request , MultipartFile image);
    void adminUpdateUser(MultipartFile image,AdminUpdateUserRequest request , Integer userId);

    void delete(Set<Integer> ids);

    void  likeBook(Integer bookId);
    void  unLikeBook(Integer bookId);
    void readBook(Integer bookId);
    void deleteReadBook(Integer bookId);

}
