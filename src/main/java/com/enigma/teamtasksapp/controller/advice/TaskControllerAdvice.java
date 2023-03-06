package com.enigma.teamtasksapp.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.enigma.teamtasksapp.controller.TaskController;
import com.enigma.teamtasksapp.dto.ApiExceptionDTO;
import com.enigma.teamtasksapp.exception.InvalidRequestParameterKeyException;
import com.enigma.teamtasksapp.exception.InvalidRequestParameterValueException;
import com.enigma.teamtasksapp.exception.task.CannotAdvanceTaskException;
import com.enigma.teamtasksapp.exception.task.TaskNotFoundException;

@RestControllerAdvice(basePackageClasses = TaskController.class)
public class TaskControllerAdvice {
    
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiExceptionDTO handleTaskNotFoundException(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }

    @ExceptionHandler(CannotAdvanceTaskException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiExceptionDTO handleCannotAdvanceTaskException(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }

    @ExceptionHandler({
        InvalidRequestParameterKeyException.class,
        InvalidRequestParameterValueException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiExceptionDTO handleBadRequests(Exception e) {
        return ApiExceptionDTO.of(e.getMessage());
    }
    
}
