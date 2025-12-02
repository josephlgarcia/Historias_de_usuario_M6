# Implementación de JWT y Control de Acceso por Roles

## 📋 Resumen

Se ha implementado un sistema completo de autenticación con JWT (JSON Web Tokens) y control de acceso basado en roles (RBAC) siguiendo arquitectura hexagonal y principios SOLID.

## 🏗️ Arquitectura Implementada

### Puertos de Entrada (Domain Ports - In)
- **LoginUseCase**: Puerto para autenticar usuarios
- **RegisterUseCase**: Puerto para registrar nuevos usuarios  
- **TokenProvider**: Puerto para generar y validar tokens JWT
- **UserDetailsProvider**: Puerto para cargar detalles del usuario

### Puertos de Salida (Domain Ports - Out)
- **UserRepository**: Puerto para acceso a persistencia de usuarios

### Casos de Uso (Application Layer)
- **LoginUseCaseImpl**: Implementa autenticación con validación de credenciales
- **RegisterUseCaseImpl**: Implementa registro con encriptación de contraseña y rol por defecto

### Adaptadores (Infrastructure Layer)
- **JwtTokenProvider**: Implementa generación y validación de tokens JWT usando JJWT 0.11.5
- **UserRepositoryAdapter**: Adaptador que convierte entidades JPA a modelos de dominio
- **UserRepositoryJpa**: Interfaz JPA para acceso a base de datos
- **CustomUserDetailsService**: Adaptador de Spring Security UserDetailsService
- **UserDetailsProviderAdapter**: Adaptador del puerto UserDetailsProvider
- **JwtAuthenticationFilter**: Filtro que intercepta solicitudes y valida tokens JWT

### Configuración de Seguridad
- **SecurityConfig**: Configuración central de Spring Security
  - Deshabilita CSRF para APIs REST
  - Configura sesiones sin estado (STATELESS)
  - Define autorización por roles con @PreAuthorize
  - Integra filtro JWT en cadena de seguridad

## 🔐 Control de Acceso por Roles

### ROLE_USER (Usuario Estándar)
- ✅ **GET** /api/v1/event/* - Leer eventos
- ✅ **GET** /api/v1/venue/* - Leer lugares
- ❌ POST, PUT, DELETE - No permitido

### ROLE_ADMIN (Administrador)
- ✅ **GET** /api/v1/event/* - Leer eventos
- ✅ **POST** /api/v1/event/* - Crear eventos
- ✅ **PUT** /api/v1/event/* - Actualizar eventos
- ✅ **DELETE** /api/v1/event/* - Eliminar eventos
- ✅ **GET** /api/v1/venue/* - Leer lugares
- ✅ **POST** /api/v1/venue/* - Crear lugares
- ✅ **PUT** /api/v1/venue/* - Actualizar lugares
- ✅ **DELETE** /api/v1/venue/* - Eliminar lugares

## 📚 Endpoints de Autenticación

### POST /api/v1/auth/login
**Descripción**: Autentica un usuario y retorna token JWT

**Solicitud**:
```json
{
  "username": "usuario",
  "password": "contraseña"
}
```

**Respuesta (200)**:
```json
{
  "data": {
    "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "userId": 1,
    "username": "usuario",
    "email": "usuario@example.com",
    "expiresIn": 3600000
  },
  "message": "Login exitoso",
  "success": true
}
```

### POST /api/v1/auth/register
**Descripción**: Registra un nuevo usuario en el sistema

**Solicitud**:
```json
{
  "username": "nuevouser",
  "email": "nuevo@example.com",
  "password": "contraseña123",
  "firstName": "Nuevo",
  "lastName": "Usuario"
}
```

**Respuesta (200)**:
```json
{
  "data": {
    "id": 3,
    "username": "nuevouser",
    "email": "nuevo@example.com",
    "firstName": "Nuevo",
    "lastName": "Usuario",
    "roles": [
      {
        "id": 1,
        "name": "ROLE_USER",
        "description": "Usuario estándar con acceso de solo lectura"
      }
    ],
    "message": "Usuario registrado exitosamente"
  },
  "message": "Usuario registrado exitosamente",
  "success": true
}
```

## 🔑 Uso del Token JWT

### En Encabezados HTTP
```
Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...
```

### Estructura del Token
El token JWT contiene los siguientes claims:
- **sub**: username (nombre de usuario)
- **userId**: ID del usuario
- **email**: Email del usuario
- **roles**: Lista de roles del usuario
- **iat**: Fecha de emisión
- **exp**: Fecha de expiración

## 🛠️ Tecnologías Utilizadas

- **Spring Boot 3.3.4**: Framework principal
- **Spring Security 6**: Autenticación y autorización
- **JJWT 0.11.5**: Generación y validación de tokens JWT
- **BCrypt**: Encriptación de contraseñas
- **MapStruct 1.5.5**: Mapeo de objetos
- **Lombok 1.18.32**: Reducción de boilerplate
- **PostgreSQL**: Base de datos

## 📁 Estructura de Carpetas Nuevas

```
infrastructure/
├── config/security/
│   ├── SecurityConfig.java                    (Configuración principal)
│   ├── JwtTokenProvider.java                  (Generador de tokens)
│   ├── JwtAuthenticationFilter.java           (Filtro de autenticación)
│   ├── CustomUserDetailsService.java          (Detalles del usuario)
│   └── UserDetailsProviderAdapter.java        (Adaptador del puerto)
├── repositories/
│   ├── JpaUserRepository.java                 (Repositorio JPA)
│   └── UserRepositoryAdapter.java             (Adaptador del repositorio)
├── mappers/
│   ├── UserMapper.java                        (Mapper de usuario)
│   └── RoleMapper.java                        (Mapper de rol)
├── web/
│   ├── controller/
│   │   └── AuthController.java                (Endpoints de autenticación)
│   └── dto/auth/
│       ├── LoginRequest.java
│       ├── LoginResponse.java
│       ├── RegisterRequest.java
│       └── RegisterResponse.java
└── util/exception/
    ├── UnauthorizedException.java             (401)
    ├── ForbiddenException.java                (403)
    ├── JwtException.java                      (Error JWT)
    └── ResourceNotFoundException.java         (404)

domain/
├── ports/
│   ├── in/auth/
│   │   ├── LoginUseCase.java
│   │   ├── RegisterUseCase.java
│   │   ├── TokenProvider.java
│   │   └── UserDetailsProvider.java
│   └── out/
│       └── UserRepository.java

application/usecases/auth/
├── LoginUseCaseImpl.java
└── RegisterUseCaseImpl.java
```

## 🔄 Flujo de Autenticación

1. Usuario envía credenciales a `/api/v1/auth/login`
2. `LoginUseCaseImpl` valida credenciales contra BCrypt
3. Si son válidas, `JwtTokenProvider` genera un token JWT
4. Token se retorna al cliente
5. Cliente incluye token en encabezado `Authorization: Bearer <token>`
6. `JwtAuthenticationFilter` intercepta la solicitud
7. Extrae y valida el token
8. Si es válido, establece el contexto de seguridad
9. `SecurityConfig` verifica autorización basada en roles
10. Si tiene permiso, ejecuta el endpoint; sino retorna 403

## 📊 Base de Datos

### Tablas
- **users**: Información del usuario
- **roles**: Definición de roles
- **user_roles**: Relación muchos a muchos entre usuarios y roles

### Datos Iniciales
```sql
-- Roles
ROLE_USER   - Usuario estándar (solo lectura)
ROLE_ADMIN  - Administrador (acceso completo)

-- Usuarios de prueba
Username: user      / Password: password123  / Rol: USER
Username: admin     / Password: admin123     / Rol: ADMIN, USER
```

## ⚙️ Configuración

### Variables de Entorno
```env
JWT_SECRET=tu_secreto_muy_largo_y_seguro_minimo_256_bits
JWT_EXPIRATION=3600000  # Milisegundos (1 hora)
```

### application.properties
```properties
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}
```

## 🧪 Ejemplos de Uso

### Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user", "password":"password123"}'
```

### Acceder a Evento
```bash
curl -X GET http://localhost:8080/api/v1/event/1 \
  -H "Authorization: Bearer <token>"
```

### Crear Evento (Solo Admin)
```bash
curl -X POST http://localhost:8080/api/v1/event \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{...}'
```

## ✅ Principios SOLID Aplicados

### Single Responsibility Principle (SRP)
- Cada clase tiene una única responsabilidad
- JwtTokenProvider solo maneja tokens
- LoginUseCaseImpl solo valida credenciales

### Open/Closed Principle (OCP)
- Arquitectura hexagonal permite extensión sin modificación
- Nuevos métodos de autenticación pueden agregarse sin cambiar código existente

### Liskov Substitution Principle (LSP)
- Los adaptadores pueden sustituir las interfaces sin problemas
- UserRepositoryAdapter implementa correctamente UserRepository

### Interface Segregation Principle (ISP)
- Puertos específicos (LoginUseCase, RegisterUseCase, TokenProvider)
- Clientes no dependen de métodos que no usan

### Dependency Inversion Principle (DIP)
- El código depende de abstracciones (puertos)
- Las implementaciones inyectan dependencias

## 🔒 Seguridad

### Mejores Prácticas Implementadas
✅ Contraseñas encriptadas con BCrypt  
✅ Tokens JWT con firma HMAC-SHA512  
✅ Validación de token en cada solicitud  
✅ Sesiones sin estado (STATELESS)  
✅ CSRF deshabilitado para API REST  
✅ Control de acceso granular por roles  
✅ Excepciones personalizadas para errores de autenticación  
✅ Manejo centralizado de errores  

### No Usado (Deprecated)
❌ Métodos `parserBuilder()` de jjwt 0.12.x  
❌ Métodos `setSubject()` antiguos (v0.11.5)  
✅ API moderna de Spring Security 6  

## 📝 Próximos Pasos (Opcionales)

1. Implementar refresh tokens
2. Agregar rate limiting
3. Implementar TOTP/2FA
4. Logging de auditoría
5. Revocación de tokens
6. OAuth2/OpenID Connect

## 📖 Referencias

- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
- [JJWT Documentation](https://github.com/jwtk/jjwt)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [SOLID Principles](https://en.wikipedia.org/wiki/SOLID)
