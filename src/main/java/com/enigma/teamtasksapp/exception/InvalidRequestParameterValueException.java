package com.enigma.teamtasksapp.exception;

import java.util.Map;

public class InvalidRequestParameterValueException extends RuntimeException {
    
    public InvalidRequestParameterValueException(Map.Entry<String, String> parameter, Throwable cause) {
        super(String.format("Parameter %s has invalid value of %s", parameter.getKey(), parameter.getValue()), cause);
    }

}
