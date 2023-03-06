package com.enigma.teamtasksapp.exception.task;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {
    
    public TaskNotFoundException(UUID uuid) {
        super(String.format("Task with UUID %s was not found", uuid.toString()));
    }

}
