package com.example.careerify.common.mappers;

import com.example.careerify.common.dto.UserRequestDTO;
import com.example.careerify.common.dto.UserResponseDTO;
import com.example.careerify.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    // Map User to UserResponseDTO
    public UserResponseDTO mapUserToDTO(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }

    // Map UserRequestDTO to User
    public User mapDTOToUser(UserRequestDTO userRequestDTO) {
        return modelMapper.map(userRequestDTO, User.class);
    }

    // Update existing User from UserRequestDTO
    public void updateUserFromDTO(UserRequestDTO updateDTO, User existingUser) {
        modelMapper.map(updateDTO, existingUser);
    }
}
