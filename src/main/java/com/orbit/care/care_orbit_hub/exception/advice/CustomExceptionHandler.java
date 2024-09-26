package com.orbit.care.care_orbit_hub.exception.advice;

import com.orbit.care.care_orbit_hub.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFoundExceptionHandler(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List errorList = ex.getBindingResult().getFieldErrors().stream().map(errorItem -> {
            Map<String, Object> errorPayload = new HashMap<>();
            errorPayload.put("field", "$."+errorItem.getField());
            errorPayload.put("message", errorItem.getDefaultMessage());
            errorPayload.put("rejectedValue", errorItem.getRejectedValue());
            return errorPayload;
        }).distinct().collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleMethodArgumentNotValidException(ConstraintViolationException ex){
       /* List errorList = ex..stream().map(errorItem -> {
            Map<String, Object> errorPayload = new HashMap<>();
            errorPayload.put("field", "$."+errorItem.getField());
            errorPayload.put("message", errorItem.getDefaultMessage());
            errorPayload.put("rejectedValue", errorItem.getRejectedValue());
            return errorPayload;
        }).distinct().collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);*/
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
    }
}
