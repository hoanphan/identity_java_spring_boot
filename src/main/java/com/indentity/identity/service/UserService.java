package com.indentity.identity.service;

import com.indentity.identity.dto.request.ApiResponse;
import com.indentity.identity.dto.request.UserCreationRequest;
import com.indentity.identity.dto.request.UserUpdateRequest;
import com.indentity.identity.entity.User;
import com.indentity.identity.exception.AppException;
import com.indentity.identity.exception.ErrorCode;
import com.indentity.identity.mapper.UserMapper;import com.indentity.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public ApiResponse<User> createUser(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("ErrorCode.USE_EXISTED");

        User user = userMapper.toUser(request);
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1000);
        apiResponse.setResult(userRepository.save(user));
       return apiResponse;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("not found user"));
    }

    public User updateUser(UserUpdateRequest request, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("not found user"));
        userMapper.mapUserUpdate(user, request);

        return userRepository.save(user);
    }

    public Boolean deleteUser(String id) {
        userRepository.deleteById(id);
        return true;
    }

}
