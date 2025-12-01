package com.events.eventManager.infrastructure.web.advice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.events.eventManager.infrastructure.util.Trace;
import jakarta.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> badRequest(
            MethodArgumentNotValidException ex, 
            HttpServletRequest req) {
        
        String traceId = Trace.currentId();
        logger.warn("Validation failed on {} - TraceId: {}", req.getRequestURI(), traceId);
        
        var fields = new LinkedHashMap<String, String>();
        ex.getBindingResult()
            .getFieldErrors()
            .forEach(f -> fields.put(f.getField(), f.getDefaultMessage()));

        var body = new LinkedHashMap<String, Object>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", 400);
        body.put("error", "Bad Request");
        body.put("message", "Validación fallida");
        body.put("path", req.getRequestURI());
        body.put("traceId", traceId);
        body.put("fields", fields);
        
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> conflict(
            IllegalArgumentException ex, 
            HttpServletRequest req) {
        
        String traceId = Trace.currentId();
        logger.warn("Conflict on {} - TraceId: {} - {}", req.getRequestURI(), traceId, ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                    "timestamp", Instant.now().toString(), 
                    "status", 409,
                    "error", "Conflict", 
                    "message", ex.getMessage(), 
                    "path", req.getRequestURI(),
                    "traceId", traceId
                ));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> notFound(
            NoSuchElementException ex, 
            HttpServletRequest req) {
        
        String traceId = Trace.currentId();
        logger.warn("Resource not found on {} - TraceId: {}", req.getRequestURI(), traceId);
        
        String message = ex.getMessage() != null && !ex.getMessage().isBlank()
            ? ex.getMessage()
            : "Resource not found";
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                    "timestamp", Instant.now().toString(), 
                    "status", 404,
                    "error", "Not Found", 
                    "message", message, 
                    "path", req.getRequestURI(),
                    "traceId", traceId
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> internalError(
            Exception ex, 
            HttpServletRequest req) {
        
        String traceId = Trace.currentId();
        logger.error("Unexpected error on {} - TraceId: {}", req.getRequestURI(), traceId, ex);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                    "timestamp", Instant.now().toString(), 
                    "status", 500,
                    "error", "Internal Server Error", 
                    "message", "An unexpected error occurred. Please contact support with the trace ID.", 
                    "path", req.getRequestURI(),
                    "traceId", traceId
                ));
    }
}