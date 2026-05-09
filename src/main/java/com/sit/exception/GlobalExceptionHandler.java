package com.sit.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sit.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntime(RuntimeException ex) {

        ApiResponse<Object> response = new ApiResponse<>(
                "error",
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}