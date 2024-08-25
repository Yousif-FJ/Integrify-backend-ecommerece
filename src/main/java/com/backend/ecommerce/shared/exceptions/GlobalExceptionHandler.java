package com.backend.ecommerce.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex){
        System.out.println("Exception handler is working");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UniquenessException.class)
    public ResponseEntity<String> handleUniquenessException(UniquenessException ex){
        System.out.println("Handling UniquenessException");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}

