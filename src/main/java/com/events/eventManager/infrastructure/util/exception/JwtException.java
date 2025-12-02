package com.events.eventManager.infrastructure.util.exception;

/**
 * Excepción lanzada cuando hay un error con el token JWT.
 */
public class JwtException extends RuntimeException {
    
    public JwtException(String message) {
        super(message);
    }
    
    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
