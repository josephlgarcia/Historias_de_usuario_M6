package com.events.eventManager.infrastructure.util.exception;

/**
 * Excepción lanzada cuando un recurso no es encontrado (404 Not Found).
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
