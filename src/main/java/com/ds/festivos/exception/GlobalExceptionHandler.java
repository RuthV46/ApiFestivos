package com.ds.festivos.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.ds.festivos.DTOs.ValidacionDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(FechaInvalidaException.class)
    public ResponseEntity<ValidacionDTO> handleFechaInvalida(FechaInvalidaException ex) {
        return ResponseEntity.badRequest().body(new ValidacionDTO(false, ex.getMessage()));
    }
}