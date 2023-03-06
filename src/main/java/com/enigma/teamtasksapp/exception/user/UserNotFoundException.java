package com.enigma.teamtasksapp.exception.user;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(UUID uuid) {
        super(String.format("User with UUID %s was not found", uuid.toString()));
    }

}
