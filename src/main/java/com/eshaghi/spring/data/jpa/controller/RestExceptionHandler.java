package com.eshaghi.spring.data.jpa.controller;

import com.eshaghi.spring.data.jpa.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(CONFLICT)
    @ResponseBody
    public ResponseEntity<String> handleConflictException(DataIntegrityViolationException e) {
        return ResponseEntity.status(CONFLICT)
                .body(e.getMessage());
    }
}
