package com.events.eventManager.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.events.eventManager.domain.model.User;
import com.events.eventManager.infrastructure.entities.UserEntity;

/**
 * Mapper para convertir entre la entidad Usuario y el modelo de dominio.
 * Utiliza MapStruct para la conversión automática.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    
    /**
     * Convierte una entidad de usuario a modelo de dominio.
     * 
     * @param userEntity entidad del usuario
     * @return usuario del dominio
     */
    User entityToDomain(UserEntity userEntity);
    
    /**
     * Convierte un modelo de dominio a entidad de usuario.
     * 
     * @param user usuario del dominio
     * @return entidad del usuario
     */
    UserEntity domainToEntity(User user);
}
