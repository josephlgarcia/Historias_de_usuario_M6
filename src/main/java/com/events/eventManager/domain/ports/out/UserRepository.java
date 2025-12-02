package com.events.eventManager.domain.ports.out;

import java.util.Optional;

import com.events.eventManager.domain.model.User;

/**
 * Puerto de salida para el repositorio de usuarios.
 * Define el contrato para acceder a la persistencia de usuarios.
 */
public interface UserRepository {
    
    /**
     * Guarda un usuario en la persistencia.
     * 
     * @param user usuario a guardar
     * @return usuario guardado
     */
    User save(User user);
    
    /**
     * Busca un usuario por nombre de usuario.
     * 
     * @param username nombre de usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Busca un usuario por ID.
     * 
     * @param id ID del usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> findById(Long id);
    
    /**
     * Busca un usuario por email.
     * 
     * @param email email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el nombre de usuario dado.
     * 
     * @param username nombre de usuario
     * @return true si existe, false en caso contrario
     */
    boolean existsByUsername(String username);
    
    /**
     * Verifica si existe un usuario con el email dado.
     * 
     * @param email email del usuario
     * @return true si existe, false en caso contrario
     */
    boolean existsByEmail(String email);
}
