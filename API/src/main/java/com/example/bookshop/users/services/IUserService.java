package com.example.bookshop.users.services;

import com.example.bookshop.users.controllers.dto.users.UserCreationRequest;
import com.example.bookshop.users.controllers.dto.users.UserUpdateRequest;
import com.example.bookshop.users.controllers.dto.users.UserResponse;
import com.example.bookshop.users.controllers.dto.users.UserUpdateRoleRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface IUserService {
    UserResponse getUserById(Integer id) ;
    List<UserResponse> getAllUsers(Pageable pageable);
    UserResponse getMyInfo();

    UserResponse createUser(UserCreationRequest request);
    UserResponse updateUser(UserUpdateRequest request);
    void updateUserImage(MultipartFile  image);
    void updateRoleUser(UserUpdateRoleRequest request);

    void delete(Set<Integer> ids);

//    String addFavouriteBook(Integer id , String token);
//    void  addReadChapter(Integer id);
}
