package com.indentity.identity.service;

import com.indentity.identity.dto.request.ApiResponse;
import com.indentity.identity.dto.request.UserCreationRequest;
import com.indentity.identity.dto.request.UserUpdateRequest;
import com.indentity.identity.dto.response.UserResponse;
import com.indentity.identity.entity.User;
import com.indentity.identity.enums.Role;import com.indentity.identity.exception.AppException;
import com.indentity.identity.exception.ErrorCode;
import com.indentity.identity.mapper.UserMapper;import com.indentity.identity.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public ApiResponse<UserResponse> createUser(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("ErrorCode.USE_EXISTED");

        User user = userMapper.toUser(request);

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(userMapper.toUserResponse(userRepository.save(user)));

       return apiResponse;
    }

    public ApiResponse<List<UserResponse>> getUsers() {
       return ApiResponse.<List<UserResponse>>builder().result(
               userRepository.findAll().stream().map(user -> userMapper.toUserResponse(user)
               ).toList()).build();
    }

    public ApiResponse<UserResponse> getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("not found user"));
        return ApiResponse.<UserResponse>builder().result(userMapper.toUserResponse(user)).build();
    }

    public ApiResponse<UserResponse> updateUser(UserUpdateRequest request, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("not found user"));
        userMapper.mapUserUpdate(user, request);

        User userUpdate = userRepository.save(user);
        return ApiResponse.<UserResponse>builder().result(userMapper.toUserResponse(userUpdate)).build();
    }

    public Boolean deleteUser(String id) {
        userRepository.deleteById(id);
        return true;
    }

}
