package com.example.bookshop.users.mappers;

import com.example.bookshop.users.controllers.dto.users.UserCreationRequest;
import com.example.bookshop.users.controllers.dto.users.UserUpdateRequest;
import com.example.bookshop.users.controllers.dto.users.UserResponse;
import com.example.bookshop.users.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
   UserResponse userToUserResponse(UserEntity userEntity);

   @Mapping(target = "image_url", ignore = true)
   UserEntity userToUserEntity(UserCreationRequest userCreationRequest);


   @Mapping(target = "roles" , ignore = true)
   @Mapping(target = "image_url", ignore = true)
   UserEntity updateUserEntity(@MappingTarget UserEntity userEntity, UserUpdateRequest userUpdateRequest);

}
