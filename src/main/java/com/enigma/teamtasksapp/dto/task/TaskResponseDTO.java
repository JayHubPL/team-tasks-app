package com.enigma.teamtasksapp.dto.task;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.enigma.teamtasksapp.model.Task;
import com.enigma.teamtasksapp.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskResponseDTO {
    
    private String title;
    private String description;
    private LocalDateTime completionDate;
    private UUID uuid;
    private Set<User> assignedUsers;
    private Task.Status status;

}
