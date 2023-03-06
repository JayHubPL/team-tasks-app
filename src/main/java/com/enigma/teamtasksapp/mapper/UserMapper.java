package com.enigma.teamtasksapp.mapper;

import org.springframework.stereotype.Component;

import com.enigma.teamtasksapp.dto.user.UserRequestDTO;
import com.enigma.teamtasksapp.dto.user.UserResponseDTO;
import com.enigma.teamtasksapp.model.User;

@Component
public class UserMapper {
    
    public User requestDtoToEntity(UserRequestDTO requestDto) {
        return User.builder()
            .name(requestDto.getName())
            .surname(requestDto.getSurname())
            .email(requestDto.getEmail())
            .build();
    }

    public UserResponseDTO entityToResponseDto(User entity) {
        return UserResponseDTO.builder()
            .name(entity.getName())
            .surname(entity.getSurname())
            .email(entity.getEmail())
            .uuid(entity.getUuid())
            .tasks(entity.getTasks())
            .build();
    }

}
