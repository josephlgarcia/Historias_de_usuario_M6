package com.events.eventManager.domain.ports.in.auth;

import java.util.Optional;

import com.events.eventManager.domain.model.User;

/**
 * Puerto de entrada para cargar detalles del usuario.
 * Define el contrato para recuperar información del usuario del sistema.
 */
public interface UserDetailsProvider {
    
    /**
     * Carga un usuario por su nombre de usuario.
     * 
     * @param username nombre de usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> loadUserByUsername(String username);
    
    /**
     * Carga un usuario por su ID.
     * 
     * @param userId ID del usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> loadUserById(Long userId);
}
