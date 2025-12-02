package com.events.eventManager.application.usecases.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.events.eventManager.domain.model.User;
import com.events.eventManager.domain.ports.in.auth.LoginUseCase;
import com.events.eventManager.domain.ports.out.UserRepository;
import com.events.eventManager.infrastructure.util.exception.UnauthorizedException;

/**
 * Implementación del caso de uso de login.
 * Autenticación de usuarios con validación de credenciales.
 */
@Service
public class LoginUseCaseImpl implements LoginUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public LoginUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Autentica un usuario validando sus credenciales.
     * 
     * @param username nombre de usuario
     * @param password contraseña
     * @return Usuario autenticado
     * @throws UnauthorizedException si las credenciales son inválidas
     */
    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UnauthorizedException("Usuario o contraseña inválidos"));
        
        if (!user.isEnabled()) {
            throw new UnauthorizedException("El usuario está deshabilitado");
        }
        
        if (!user.isAccountNonLocked()) {
            throw new UnauthorizedException("El usuario está bloqueado");
        }
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UnauthorizedException("Usuario o contraseña inválidos");
        }
        
        return user;
    }
}
