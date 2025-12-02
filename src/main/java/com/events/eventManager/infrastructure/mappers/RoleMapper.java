package com.events.eventManager.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.events.eventManager.domain.model.Role;
import com.events.eventManager.infrastructure.entities.RoleEntity;

/**
 * Mapper para convertir entre la entidad Rol y el modelo de dominio.
 * Utiliza MapStruct para la conversión automática.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    
    /**
     * Convierte una entidad de rol a modelo de dominio.
     * 
     * @param roleEntity entidad del rol
     * @return rol del dominio
     */
    Role entityToDomain(RoleEntity roleEntity);
    
    /**
     * Convierte un modelo de dominio a entidad de rol.
     * 
     * @param role rol del dominio
     * @return entidad del rol
     */
    RoleEntity domainToEntity(Role role);
}
