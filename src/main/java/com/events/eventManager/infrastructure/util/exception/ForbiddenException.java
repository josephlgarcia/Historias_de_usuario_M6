package com.events.eventManager.infrastructure.util.exception;

/**
 * Excepción lanzada cuando hay un error de acceso prohibido (403 Forbidden).
 */
public class ForbiddenException extends RuntimeException {
    
    public ForbiddenException(String message) {
        super(message);
    }
    
    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}
