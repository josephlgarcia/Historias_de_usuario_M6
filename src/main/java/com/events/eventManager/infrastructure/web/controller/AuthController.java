package com.events.eventManager.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.events.eventManager.domain.model.User;
import com.events.eventManager.domain.ports.in.auth.LoginUseCase;
import com.events.eventManager.domain.ports.in.auth.RegisterUseCase;
import com.events.eventManager.domain.ports.in.auth.TokenProvider;
import com.events.eventManager.infrastructure.util.AppResponse;
import com.events.eventManager.infrastructure.web.dto.auth.LoginRequest;
import com.events.eventManager.infrastructure.web.dto.auth.LoginResponse;
import com.events.eventManager.infrastructure.web.dto.auth.RegisterRequest;
import com.events.eventManager.infrastructure.web.dto.auth.RegisterResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador para la autenticación y autorización.
 * Proporciona endpoints para login y registro de usuarios.
 */
@Tag(name = "Authentication", description = "API for user authentication and authorization")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    private final TokenProvider tokenProvider;
    
    public AuthController(LoginUseCase loginUseCase, RegisterUseCase registerUseCase,
                         TokenProvider tokenProvider) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
        this.tokenProvider = tokenProvider;
    }
    
    /**
     * Autentica un usuario y retorna un token JWT.
     * 
     * @param loginRequest credenciales del usuario
     * @return token JWT si la autenticación es exitosa
     */
    @Operation(summary = "Login user", description = "Authenticate user and get JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AppResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = loginUseCase.login(loginRequest.getUsername(), loginRequest.getPassword());
        
        String token = tokenProvider.generateToken(user);
        Long expiresIn = tokenProvider.getTokenExpirationMs();
        
        LoginResponse response = new LoginResponse(
            token,
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            expiresIn
        );
        
        AppResponse<LoginResponse> appResponse = AppResponse.withMessage(response, "Login exitoso");
        return ResponseEntity.ok(appResponse);
    }
    
    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param registerRequest datos del usuario a registrar
     * @return usuario registrado
     */
    @Operation(summary = "Register user", description = "Register a new user in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully",
            content = @Content(schema = @Schema(implementation = RegisterResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid data or user already exists", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<AppResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        
        User registeredUser = registerUseCase.register(user);
        
        RegisterResponse response = new RegisterResponse();
        response.setId(registeredUser.getId());
        response.setUsername(registeredUser.getUsername());
        response.setEmail(registeredUser.getEmail());
        response.setFirstName(registeredUser.getFirstName());
        response.setLastName(registeredUser.getLastName());
        response.setRoles(registeredUser.getRoles());
        response.setMessage("Usuario registrado exitosamente");
        
        AppResponse<RegisterResponse> appResponse = AppResponse.withMessage(response, "Usuario registrado exitosamente");
        return ResponseEntity.ok(appResponse);
    }
}
