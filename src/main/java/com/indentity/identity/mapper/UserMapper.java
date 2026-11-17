package com.indentity.identity.mapper;

import com.indentity.identity.dto.request.UserCreationRequest;
import com.indentity.identity.dto.request.UserUpdateRequest;import com.indentity.identity.entity.User;
import org.mapstruct.Mapper;import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    void mapUserUpdate(@MappingTarget User user, UserUpdateRequest request);
}
