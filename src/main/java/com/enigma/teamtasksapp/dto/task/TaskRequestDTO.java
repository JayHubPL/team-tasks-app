package com.enigma.teamtasksapp.dto.task;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TaskRequestDTO {
    
    private String title;
    private String description;
    private LocalDateTime completionDate;

}
