package com.lisoft.exagen.infrastructure.config;

import com.lisoft.exagen.application.dtos.ResponseWrapper;
import com.lisoft.exagen.domain.exceptions.InvalidArgumentException;
import com.lisoft.exagen.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.lisoft.exagen.domain.utils.Constants.UNEXPECTED_ERROR_MESSAGE;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidArgumentException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleInvalidArgumentException(InvalidArgumentException exception) {
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        false,
                        exception.getMessage(),
                        null
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        false,
                        exception.getMessage(),
                        null
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Object>> handleException(Exception exception) {
        return new ResponseEntity<>(
                new ResponseWrapper<>(
                        false,
                        UNEXPECTED_ERROR_MESSAGE + exception.getMessage(),
                        null
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
