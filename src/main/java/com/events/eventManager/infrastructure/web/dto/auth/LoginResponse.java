package com.events.eventManager.infrastructure.web.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para respuesta de login.
 * Contiene el token JWT y información del usuario.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    private String token;
    private String tokenType = "Bearer";
    private Long userId;
    private String username;
    private String email;
    private Long expiresIn;
    
    public LoginResponse(String token, Long userId, String username, String email, Long expiresIn) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.expiresIn = expiresIn;
    }
}
