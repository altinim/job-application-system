package com.example.careerify.service;

import com.example.careerify.common.dto.UserRequestDTO;
import com.example.careerify.common.dto.UserResponseDTO;
import com.example.careerify.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    UserResponseDTO getUserById(UUID id);
    UserDetailsService userDetailsService();
    User save(User newUser);
    List<UserResponseDTO> getAllUsers();
    void updateUser(UUID id, UserRequestDTO updateDTO);
    void deleteUser(UUID userId);
    boolean existsByEmail(String email) ;
}
