package com.events.eventManager.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.events.eventManager.infrastructure.entities.UserEntity;

/**
 * Repositorio JPA para la entidad de usuario.
 * Proporciona métodos para consultas de usuario específicas.
 */
@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
    
    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param username nombre de usuario
     * @return Optional con el usuario si existe
     */
    Optional<UserEntity> findByUsername(String username);
    
    /**
     * Busca un usuario por su email.
     * 
     * @param email email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<UserEntity> findByEmail(String email);
    
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
