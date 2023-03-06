package com.enigma.teamtasksapp.exception.user;

public class InvalidEmailFormatException extends RuntimeException {
    
    public InvalidEmailFormatException(String email) {
        super(String.format("%s is not a valid email", email));
    }

}
