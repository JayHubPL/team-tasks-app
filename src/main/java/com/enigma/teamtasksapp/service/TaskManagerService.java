package com.enigma.teamtasksapp.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.enigma.teamtasksapp.dto.task.TaskResponseDTO;
import com.enigma.teamtasksapp.exception.task.CannotAdvanceTaskException;
import com.enigma.teamtasksapp.exception.task.TaskNotFoundException;
import com.enigma.teamtasksapp.exception.user.UserNotFoundException;
import com.enigma.teamtasksapp.mapper.TaskMapper;
import com.enigma.teamtasksapp.model.Task;
import com.enigma.teamtasksapp.model.User;
import com.enigma.teamtasksapp.repository.TaskRepository;
import com.enigma.teamtasksapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskManagerService {
    
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public void assignUserToTask(UUID userUuid, UUID taskUuid) {
        User user = userRepository.findByUuid(userUuid)
            .orElseThrow(() -> new UserNotFoundException(userUuid));
        Task task = taskRepository.findByUuid(taskUuid)
            .orElseThrow(() -> new TaskNotFoundException(taskUuid));
        user.assignTask(task);
        userRepository.save(user);
    }

    public void removeUserFromTask(UUID userUuid, UUID taskUuid) {
        User user = userRepository.findByUuid(userUuid)
            .orElseThrow(() -> new UserNotFoundException(userUuid));
        Task task = taskRepository.findByUuid(taskUuid)
            .orElseThrow(() -> new TaskNotFoundException(taskUuid));
        user.removeTask(task);
        userRepository.save(user);
    }

    public TaskResponseDTO advance(UUID taskUuid) {
        Task task = taskRepository.findByUuid(taskUuid)
            .orElseThrow(() -> new TaskNotFoundException(taskUuid));
        switch (task.getStatus()) {
            case TODO -> task.setStatus(Task.Status.WORK_IN_PROGRESS);
            case WORK_IN_PROGRESS -> task.setStatus(Task.Status.COMPLETED);
            case COMPLETED -> throw new CannotAdvanceTaskException(task.getUuid());
        }
        taskRepository.save(task);
        return taskMapper.entityToResponseDto(task);
    }

}
