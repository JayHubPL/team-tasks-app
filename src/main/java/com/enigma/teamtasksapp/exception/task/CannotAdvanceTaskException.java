package com.enigma.teamtasksapp.exception.task;

import java.util.UUID;

public class CannotAdvanceTaskException extends RuntimeException {
    
    public CannotAdvanceTaskException(UUID uuid) {
        super(String.format("Task with UUID %s could not be advanced, because it is COMPLETED", uuid.toString()));
    }

}
