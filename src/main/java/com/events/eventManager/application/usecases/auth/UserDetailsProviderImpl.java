package com.events.eventManager.application.usecases.auth;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.events.eventManager.domain.model.User;
import com.events.eventManager.domain.ports.in.auth.UserDetailsProvider;
import com.events.eventManager.domain.ports.out.UserRepositoryPort;

/**
 * Implementación del proveedor de detalles del usuario.
 * Proporciona acceso a información del usuario para autenticación y autorización.
 */
@Service
@Primary
public class UserDetailsProviderImpl implements UserDetailsProvider {
    
    private final UserRepositoryPort userRepository;
    
    public UserDetailsProviderImpl(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Carga un usuario por su nombre de usuario.
     * 
     * @param username nombre de usuario
     * @return Optional con el usuario si existe
     */
    @Override
    public Optional<User> loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    /**
     * Carga un usuario por su ID.
     * 
     * @param userId ID del usuario
     * @return Optional con el usuario si existe
     */
    @Override
    public Optional<User> loadUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
