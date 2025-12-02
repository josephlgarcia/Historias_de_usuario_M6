package com.events.eventManager.infrastructure.repositories;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.events.eventManager.domain.model.User;
import com.events.eventManager.domain.ports.out.UserRepository;
import com.events.eventManager.infrastructure.entities.UserEntity;
import com.events.eventManager.infrastructure.mappers.UserMapper;

/**
 * Adaptador del repositorio de usuarios.
 * Implementa el puerto de salida UserRepository usando JPA.
 */
@Component
public class UserRepositoryAdapter implements UserRepository {
    
    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;
    
    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository, UserMapper userMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userMapper = userMapper;
    }
    
    @Override
    public User save(User user) {
        UserEntity entity = userMapper.domainToEntity(user);
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
