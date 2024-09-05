package com.example.careerify.service;

import com.example.careerify.common.dto.UserRequestDTO;
import com.example.careerify.common.dto.UserResponseDTO;
import com.example.careerify.common.mappers.UserMapper;
import com.example.careerify.model.User;
import com.example.careerify.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public User save(User newUser) {
        if (newUser.getId() == null) {
            newUser.setCreatedAt(LocalDateTime.now());
        }
        newUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        try {
            User user = userMapper.mapDTOToUser(userRequestDTO);
            User savedUser = userRepository.save(user);
            return userMapper.mapUserToDTO(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error creating user. Duplicate entry.", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error creating user. Illegal argument.", e);
        }
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        return userMapper.mapUserToDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::mapUserToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(UUID userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        userRepository.delete(existingUser);
    }

    @Override
    @Transactional
    public void updateUser(UUID id, UserRequestDTO updateDTO) throws EntityNotFoundException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
        userMapper.updateUserFromDTO(updateDTO, existingUser);
        userRepository.save(existingUser);
    }
}
