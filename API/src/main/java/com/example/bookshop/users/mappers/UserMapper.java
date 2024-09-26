package com.example.bookshop.users.mappers;

import com.example.bookshop.users.controllers.dto.users.AdminUpdateUserRequest;
import com.example.bookshop.users.controllers.dto.users.UserCreationRequest;
import com.example.bookshop.users.controllers.dto.users.UserUpdateRequest;
import com.example.bookshop.users.controllers.dto.users.UserResponse;
import com.example.bookshop.users.models.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring" , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {


   UserResponse userToUserResponse(UserEntity userEntity);


   @Mapping(target = "image_url", ignore = true)
   @Mapping(target = "role", ignore = true)
   UserEntity userToUserEntity(UserCreationRequest userCreationRequest);

   @Mapping(target = "image_url", ignore = true)
   @Mapping(target = "password", ignore = true)
   void  updateUserEntity(@MappingTarget UserEntity userEntity, UserUpdateRequest userUpdateRequest);

   @Mapping(target = "image_url", ignore = true)
   @Mapping(target = "password", ignore = true)
   void updateUserEntityByAdmin(@MappingTarget UserEntity userEntity, AdminUpdateUserRequest request);



}
