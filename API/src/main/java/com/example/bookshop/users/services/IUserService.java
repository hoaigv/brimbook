package com.example.bookshop.users.services;

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
    UserResponse getMyInfo();

    UserResponse createUser(UserCreationRequest request);
    void updateUser(UserUpdateRequest request);
    void updateUserImage(MultipartFile  image);
    void adminUpdateUser(AdminUpdateUserRequest request , Integer userId);

    void delete(Set<Integer> ids);

//    String addFavouriteBook(Integer id , String token);
//    void  addReadChapter(Integer id);
}
