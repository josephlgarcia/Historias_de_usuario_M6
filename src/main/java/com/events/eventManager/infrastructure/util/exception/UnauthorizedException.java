package com.events.eventManager.infrastructure.util.exception;

/**
 * Excepción lanzada cuando el usuario no está autorizado para acceder a un recurso.
 */
public class UnauthorizedException extends RuntimeException {
    
    public UnauthorizedException(String message) {
        super(message);
    }
    
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
