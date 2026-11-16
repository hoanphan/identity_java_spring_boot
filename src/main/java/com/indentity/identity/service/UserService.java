package com.indentity.identity.service;

import com.indentity.identity.dto.request.UserCreationRequest;
import com.indentity.identity.dto.request.UserUpdateRequest;
import com.indentity.identity.entity.User;
import com.indentity.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Exits user name");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setDob(request.getDob());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());

       return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("not found user"));
    }

    public User updateUser(UserUpdateRequest request, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("not found user"));
        user.setUsername(request.getUsername());
        user.setDob(request.getDob());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    public Boolean deleteUser(String id) {
        userRepository.deleteById(id);
        return true;
    }

}
