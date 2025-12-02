package com.events.eventManager.application.usecases.auth;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.events.eventManager.domain.model.Role;
import com.events.eventManager.domain.model.User;
import com.events.eventManager.domain.ports.in.auth.RegisterUseCase;
import com.events.eventManager.domain.ports.out.UserRepository;

/**
 * Implementación del caso de uso de registro.
 * Registra nuevos usuarios en el sistema con rol de usuario por defecto.
 */
@Service
public class RegisterUseCaseImpl implements RegisterUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public RegisterUseCaseImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Registra un nuevo usuario en el sistema.
     * Por defecto se asigna el rol USER.
     * 
     * @param user datos del usuario a registrar
     * @return Usuario registrado con contraseña hasheada
     * @throws IllegalArgumentException si el usuario o email ya existe
     */
    @Override
    public User register(User user) {
        // Validar que el usuario no exista
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        
        // Hashear la contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Asignar rol por defecto (USER)
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Set<Role> defaultRoles = new HashSet<>();
            defaultRoles.add(new Role("ROLE_USER"));
            user.setRoles(defaultRoles);
        }
        
        return userRepository.save(user);
    }
}
