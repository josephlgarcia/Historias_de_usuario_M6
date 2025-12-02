package com.events.eventManager.infrastructure.config.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.events.eventManager.domain.model.User;
import com.events.eventManager.domain.ports.in.auth.TokenProvider;
import com.events.eventManager.infrastructure.util.exception.JwtException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Proveedor de tokens JWT.
 * Genera, valida y extrae información de tokens JWT.
 */
@Component
public class JwtTokenProvider implements TokenProvider {
    
    @Value("${jwt.secret}")
    private String jwtSecret;
    
    @Value("${jwt.expiration}")
    private long jwtExpirationMs;
    
    /**
     * Genera una clave segura a partir del secreto.
     * 
     * @return clave segura
     */
    private SecretKey getSigningKey() {
        byte[] decodedKey = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(decodedKey);
    }
    
    /**
     * Genera un token JWT para un usuario.
     * 
     * @param user usuario
     * @return token JWT
     */
    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles().stream()
                    .map(role -> role.getName())
                    .toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    
    /**
     * Extrae el nombre de usuario del token.
     * 
     * @param token token JWT
     * @return nombre de usuario
     */
    @Override
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    
    /**
     * Extrae el ID del usuario del token.
     * 
     * @param token token JWT
     * @return ID del usuario
     */
    @Override
    public Long extractUserId(String token) {
        return extractAllClaims(token).get("userId", Long.class);
    }
    
    /**
     * Valida un token JWT.
     * 
     * @param token token JWT
     * @return true si es válido, false en caso contrario
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new JwtException("Token JWT inválido o expirado", e);
        }
    }
    
    /**
     * Obtiene el tiempo de expiración del token en milisegundos.
     * 
     * @return tiempo de expiración
     */
    @Override
    public long getTokenExpirationMs() {
        return jwtExpirationMs;
    }
    
    /**
     * Extrae todos los claims del token.
     * 
     * @param token token JWT
     * @return claims
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            throw new JwtException("No se pudo extraer los claims del token", e);
        }
    }
}
