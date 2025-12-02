package com.events.eventManager.domain.ports.in.auth;

import com.events.eventManager.domain.model.User;

/**
 * Puerto de entrada para el caso de uso de login.
 * Define el contrato para autenticar un usuario y obtener un token JWT.
 */
public interface LoginUseCase {
    
    /**
     * Autentica un usuario con sus credenciales.
     * 
     * @param username nombre de usuario
     * @param password contraseña
     * @return Usuario autenticado
     * @throws com.events.eventManager.infrastructure.util.exception.UnauthorizedException si las credenciales son inválidas
     */
    User login(String username, String password);
}
