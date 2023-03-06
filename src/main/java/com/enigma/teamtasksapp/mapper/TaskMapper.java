package com.enigma.teamtasksapp.mapper;

import org.springframework.stereotype.Component;

import com.enigma.teamtasksapp.dto.task.TaskRequestDTO;
import com.enigma.teamtasksapp.dto.task.TaskResponseDTO;
import com.enigma.teamtasksapp.model.Task;

@Component
public class TaskMapper {
    
    public Task requestDtoToEntity(TaskRequestDTO requestDto) {
        return Task.builder()
            .title(requestDto.getTitle())
            .description(requestDto.getDescription())
            .completionDate(requestDto.getCompletionDate())
            .build();
    }

    public TaskResponseDTO entityToResponseDto(Task entity) {
        return TaskResponseDTO.builder()
            .uuid(entity.getUuid())
            .title(entity.getTitle())
            .description(entity.getDescription())
            .completionDate(entity.getCompletionDate())
            .assignedUsers(entity.getUsers())
            .build();
    }

}
