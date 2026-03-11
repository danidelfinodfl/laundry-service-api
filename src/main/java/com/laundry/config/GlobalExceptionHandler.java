package com.laundry.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CepNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleCepNaoEncontrado(CepNaoEncontradoException ex) {

        return Map.of(
                "erro", ex.getMessage()
        );
    }
}