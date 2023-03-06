package com.enigma.teamtasksapp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import com.enigma.teamtasksapp.dto.task.TaskRequestDTO;
import com.enigma.teamtasksapp.dto.task.TaskResponseDTO;
import com.enigma.teamtasksapp.exception.InvalidRequestParameterKeyException;
import com.enigma.teamtasksapp.exception.InvalidRequestParameterValueException;
import com.enigma.teamtasksapp.exception.task.TaskNotFoundException;
import com.enigma.teamtasksapp.mapper.TaskMapper;
import com.enigma.teamtasksapp.model.Task;
import com.enigma.teamtasksapp.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskCrudService {
    
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public UUID create(TaskRequestDTO requestDto) {
        validateRequestDto(requestDto);
        UUID uuid = UUID.randomUUID();
        Task entity = taskMapper.requestDtoToEntity(requestDto);
        entity.setUuid(uuid);
        entity = taskRepository.save(entity);
        return entity.getUuid();
    }

    public TaskResponseDTO read(UUID uuid) {
        Task entity = taskRepository.findByUuid(uuid)
            .orElseThrow(() -> new TaskNotFoundException(uuid));
        return taskMapper.entityToResponseDto(entity);
    }

    public List<TaskResponseDTO> read(Map<String, String> params) {
        return taskRepository.findAll().stream()
            .filter(paramsToPredicate(params))
            .map(taskMapper::entityToResponseDto)
            .toList();
    }

    public TaskResponseDTO update(TaskRequestDTO requestDto, UUID uuid) {
        validateRequestDto(requestDto);
        Task entity = taskRepository.findByUuid(uuid)
            .orElseThrow(() -> new TaskNotFoundException(uuid));
        entity.setTitle(requestDto.getTitle());
        entity.setDescription(requestDto.getDescription());
        entity.setCompletionDate(requestDto.getCompletionDate());
        entity = taskRepository.save(entity);
        return taskMapper.entityToResponseDto(entity);
    }

    public void delete(UUID uuid) {
        Task entity = taskRepository.findByUuid(uuid)
            .orElseThrow(() -> new TaskNotFoundException(uuid));
        taskRepository.delete(entity);
    }

    private void validateRequestDto(TaskRequestDTO requestDto) {
        // TODO not essential
    }

    private Predicate<Task> paramsToPredicate(Map<String, String> params) {
        return params.entrySet().stream()
            .<Predicate<Task>>map(entry -> {
                try {
                    return switch (entry.getKey().toLowerCase()) {
                        case "title" -> task -> task.getTitle().equals(entry.getValue());
                        case "description" -> task -> task.getDescription().contains(entry.getValue());
                        case "completionDate" -> task -> task.getCompletionDate().equals(LocalDateTime.parse(entry.getValue()));
                        case "status" -> task -> task.getStatus().equals(Task.Status.valueOf(entry.getValue()));
                        default -> throw new InvalidRequestParameterKeyException(entry.getKey());
                    };
                } catch (DateTimeParseException | IllegalArgumentException e) {
                    throw new InvalidRequestParameterValueException(entry, e);
                }
            })
            .reduce(x -> true, Predicate::and);
    }

}
