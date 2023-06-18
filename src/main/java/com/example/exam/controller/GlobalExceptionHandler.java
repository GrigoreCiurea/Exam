package com.example.exam.controller;

import com.example.exam.exception.ProjectNotFoundException;
import com.example.exam.exception.TaskNotFoundException;
import com.example.exam.exception.UserAlreadyExistsException;
import com.example.exam.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException){
        return ResponseEntity.badRequest().body(new ErrorResponse(userAlreadyExistsException.getMessage()));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException userNotFoundException){
        return ResponseEntity.badRequest().body(new ErrorResponse(userNotFoundException.getMessage()));
    }
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProjectNotFoundException(ProjectNotFoundException projectNotFoundException){
        return ResponseEntity.badRequest().body(new ErrorResponse(projectNotFoundException.getMessage()));
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException taskNotFoundException){
        return ResponseEntity.badRequest().body(new ErrorResponse(taskNotFoundException.getMessage()));
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException runtimeException){
        return ResponseEntity.badRequest().body(new ErrorResponse(runtimeException.getMessage()));
    }


    private static class ErrorResponse{
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
