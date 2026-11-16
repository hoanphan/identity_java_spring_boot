package com.indentity.identity.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<String> handlerRuntimeException(RuntimeException runtimeException) {
        return  ResponseEntity.badRequest().body(runtimeException.getMessage());
    }
}
