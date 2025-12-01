package com.ecusol.ventanilla.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        String mensajeOriginal = ex.getMessage();
        String mensajeLimpio = mensajeOriginal.replace("java.lang.RuntimeException: ", "")
                                              .replace("500 Internal Server Error: ", "")
                                              .replace("\"", "");
        String titulo = "Error de Operación";
        if (mensajeLimpio.toLowerCase().contains("saldo")) titulo = "Fondos Insuficientes";
        if (mensajeLimpio.toLowerCase().contains("no existe")) titulo = "Cuenta no Encontrada";
        if (mensajeLimpio.toLowerCase().contains("bloquead")) titulo = "Cuenta Bloqueada";

        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("titulo", titulo);
        errorBody.put("mensaje", mensajeLimpio);

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}