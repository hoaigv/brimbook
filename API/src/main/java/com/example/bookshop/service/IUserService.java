package com.example.bookshop.service;

import com.example.bookshop.dto.users.UserCreationRequest;
import com.example.bookshop.dto.users.UserUpdateRequest;
import com.example.bookshop.dto.users.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface IUserService {
    UserResponse getUserById(Integer id) ;
    List<UserResponse> getAllUsers(Pageable pageable);
    UserResponse getMyInfo();

    UserResponse createUser(UserCreationRequest request, MultipartFile file);
    UserResponse updateUser(UserUpdateRequest request);
    UserResponse updateRoleUser(List<String> roles ,Integer userId);

    void delete(Set<Integer> ids);

    String addFavouriteBook(Integer id , String token);
    void  addReadChapter(Integer id);
}
