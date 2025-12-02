package com.events.eventManager.domain.ports.in.auth;

import com.events.eventManager.domain.model.User;

/**
 * Puerto de entrada para la generación y validación de tokens JWT.
 * Define el contrato para crear y extraer información de tokens.
 */
public interface TokenProvider {
    
    /**
     * Genera un token JWT para un usuario.
     * 
     * @param user usuario para el que generar el token
     * @return token JWT generado
     */
    String generateToken(User user);
    
    /**
     * Extrae el nombre de usuario del token JWT.
     * 
     * @param token token JWT
     * @return nombre de usuario
     */
    String extractUsername(String token);
    
    /**
     * Valida si un token JWT es válido.
     * 
     * @param token token JWT a validar
     * @return true si el token es válido, false en caso contrario
     */
    boolean validateToken(String token);
    
    /**
     * Extrae el ID del usuario del token JWT.
     * 
     * @param token token JWT
     * @return ID del usuario
     */
    Long extractUserId(String token);
    
    /**
     * Obtiene el tiempo de expiración del token en milisegundos.
     * 
     * @return tiempo de expiración
     */
    long getTokenExpirationMs();
}
