package com.events.eventManager.infrastructure.adapters;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.events.eventManager.domain.model.User;
import com.events.eventManager.domain.ports.out.UserRepositoryPort;
import com.events.eventManager.infrastructure.entities.RoleEntity;
import com.events.eventManager.infrastructure.entities.UserEntity;
import com.events.eventManager.infrastructure.mappers.UserMapper;
import com.events.eventManager.infrastructure.repositories.JpaRoleRepository;
import com.events.eventManager.infrastructure.repositories.JpaUserRepository;

/**
 * Adaptador del repositorio de usuarios.
 * Implementa el puerto de salida UserRepository usando JPA.
 */
@Component
public class UserRepositoryAdapter implements UserRepositoryPort {
    
    private final JpaUserRepository jpaUserRepository;
    private final JpaRoleRepository jpaRoleRepository;
    private final UserMapper userMapper;
    
    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository, JpaRoleRepository jpaRoleRepository, UserMapper userMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.jpaRoleRepository = jpaRoleRepository;
        this.userMapper = userMapper;
    }
    
    @Override
    public User save(User user) {
        UserEntity entity = userMapper.domainToEntity(user);

        Set<RoleEntity> roles = user.getRoles().stream()
            .map(role -> jpaRoleRepository.findByName(role.getName())
                .orElseThrow(() ->
                    new IllegalStateException("El rol no existe en la BD: " + role.getName())
                )
            )
            .collect(Collectors.toSet());
    

        entity.setRoles(roles);

        UserEntity saved = jpaUserRepository.save(entity);

        return userMapper.entityToDomain(saved);
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return jpaUserRepository.findByUsername(username)
            .map(userMapper::entityToDomain);
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
            .map(userMapper::entityToDomain);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
            .map(userMapper::entityToDomain);
    }
    
    @Override
    public boolean existsByUsername(String username) {
        return jpaUserRepository.existsByUsername(username);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}
