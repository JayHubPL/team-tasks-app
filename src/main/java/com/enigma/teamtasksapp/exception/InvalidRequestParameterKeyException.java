package com.enigma.teamtasksapp.exception;

public class InvalidRequestParameterKeyException extends RuntimeException {
    
    public InvalidRequestParameterKeyException(String parameterKey) {
        super(String.format("Parameter %s is invalid or not supported", parameterKey));
    }

}
