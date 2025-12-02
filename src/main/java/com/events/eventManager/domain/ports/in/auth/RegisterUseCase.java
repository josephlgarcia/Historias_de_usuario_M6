package com.events.eventManager.domain.ports.in.auth;

import com.events.eventManager.domain.model.User;

/**
 * Puerto de entrada para el caso de uso de registro.
 * Define el contrato para registrar un nuevo usuario en el sistema.
 */
public interface RegisterUseCase {
    
    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param user datos del usuario a registrar
     * @return Usuario registrado
     * @throws IllegalArgumentException si el usuario ya existe
     */
    User register(User user);
}
