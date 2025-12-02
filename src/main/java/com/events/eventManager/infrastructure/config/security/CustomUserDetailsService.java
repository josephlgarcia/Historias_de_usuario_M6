package com.events.eventManager.infrastructure.config.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.events.eventManager.domain.ports.in.auth.UserDetailsProvider;

/**
 * Servicio de detalles del usuario para Spring Security.
 * Implementa UserDetailsService para integración con Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserDetailsProvider userDetailsProvider;
    
    public CustomUserDetailsService(UserDetailsProvider userDetailsProvider) {
        this.userDetailsProvider = userDetailsProvider;
    }
    
    /**
     * Carga los detalles del usuario por nombre de usuario.
     * 
     * @param username nombre de usuario
     * @return UserDetails del usuario
     * @throws UsernameNotFoundException si el usuario no existe
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var domainUser = userDetailsProvider.loadUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        
        return User.builder()
            .username(domainUser.getUsername())
            .password(domainUser.getPassword())
            .authorities(domainUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList())
            .accountExpired(!domainUser.isAccountNonExpired())
            .accountLocked(!domainUser.isAccountNonLocked())
            .credentialsExpired(!domainUser.isCredentialsNonExpired())
            .disabled(!domainUser.isEnabled())
            .build();
    }
}
