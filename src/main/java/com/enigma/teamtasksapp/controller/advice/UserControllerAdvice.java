package com.enigma.teamtasksapp.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.enigma.teamtasksapp.controller.UserController;
import com.enigma.teamtasksapp.dto.ApiExceptionDTO;
import com.enigma.teamtasksapp.exception.InvalidRequestParameterKeyException;
import com.enigma.teamtasksapp.exception.user.EmailTakenException;
import com.enigma.teamtasksapp.exception.user.InvalidEmailFormatException;
import com.enigma.teamtasksapp.exception.user.UserNotFoundException;

@RestControllerAdvice(basePackageClasses = UserController.class)
public class UserControllerAdvice {
    
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiExceptionDTO handleUserNotFoundException(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }

    @ExceptionHandler(EmailTakenException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiExceptionDTO handleEmailTakenExceptions(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }

    @ExceptionHandler(InvalidEmailFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiExceptionDTO handleInvalidEmailFormatException(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }

    @ExceptionHandler(InvalidRequestParameterKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiExceptionDTO handleBadRequests(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }
    
}
