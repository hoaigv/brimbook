package com.example.bookshop.users.mappers;

import com.example.bookshop.users.controllers.dto.roles.RoleRequest;
import com.example.bookshop.users.controllers.dto.roles.RoleResponse;
import com.example.bookshop.users.models.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions" , ignore = true)
    RoleEntity roleToRoleEntity(RoleRequest request);

    RoleResponse roleToRoleResponse(RoleEntity role);

    @Mapping(target = "permissions" , ignore = true)
    @Mapping(target = "roleDescription", source = "roleDescription")
    @Mapping(target = "roleName", source = "roleDescription")
    RoleEntity roleRequestToRoleEntity(RoleRequest request , @MappingTarget RoleEntity role);
}
