package com.enigma.teamtasksapp.dto.user;

import java.util.Set;
import java.util.UUID;

import com.enigma.teamtasksapp.model.Task;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {
    
    private String name;
    private String surname;
    private String email;
    private UUID uuid;
    private Set<Task> tasks;

}
