# Arquitectura Hexagonal - JWT Implementation

## 🏗️ Visión General

La implementación de JWT sigue estrictamente la **Arquitectura Hexagonal (Puertos y Adaptadores)** con principios **SOLID** para garantizar:

- ✅ Separación de responsabilidades clara
- ✅ Independencia de frameworks
- ✅ Fácil de testear
- ✅ Flexible y mantenible
- ✅ Escalable

## 📐 Capas de Arquitectura

```
┌─────────────────────────────────────────────────────┐
│              EXTERNAL (HTTP, UI, etc)               │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│         INFRASTRUCTURE ADAPTER LAYER                 │
│  (Controllers, Filters, Repositories, Mappers)      │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│              APPLICATION LAYER                       │
│     (Use Cases Implementation)                       │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│             DOMAIN LAYER (Core)                      │
│  (Models, Ports/Interfaces)                         │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│         EXTERNAL (Database, Security)               │
└─────────────────────────────────────────────────────┘
```

## 🔌 Puertos (Interfaces)

### Puertos de Entrada (Inbound)

#### 1. LoginUseCase
```
domain/ports/in/auth/LoginUseCase.java

RESPONSABILIDAD:
  Define el contrato para autenticar un usuario

MÉTODOS:
  + login(username: String, password: String): User
```

**Implementación**:
```
application/usecases/auth/LoginUseCaseImpl.java

RESPONSABILIDADES:
  - Validar credenciales contra BCrypt
  - Verificar estado del usuario (habilitado, no bloqueado, etc)
  - Retornar usuario autenticado o lanzar UnauthorizedException
```

#### 2. RegisterUseCase
```
domain/ports/in/auth/RegisterUseCase.java

RESPONSABILIDAD:
  Define el contrato para registrar un nuevo usuario

MÉTODOS:
  + register(user: User): User
```

**Implementación**:
```
application/usecases/auth/RegisterUseCaseImpl.java

RESPONSABILIDADES:
  - Validar que el usuario no existe
  - Hashear la contraseña con BCrypt
  - Asignar rol por defecto (ROLE_USER)
  - Guardar el usuario en la base de datos
```

#### 3. TokenProvider
```
domain/ports/in/auth/TokenProvider.java

RESPONSABILIDAD:
  Define el contrato para generar y validar tokens JWT

MÉTODOS:
  + generateToken(user: User): String
  + extractUsername(token: String): String
  + extractUserId(token: String): Long
  + validateToken(token: String): boolean
  + getTokenExpirationMs(): long
```

**Implementación**:
```
infrastructure/config/security/JwtTokenProvider.java

RESPONSABILIDADES:
  - Generar tokens JWT con claims
  - Extraer información del token
  - Validar firma y expiración del token
  - Manejo de excepciones JWT
```

#### 4. UserDetailsProvider
```
domain/ports/in/auth/UserDetailsProvider.java

RESPONSABILIDAD:
  Define el contrato para cargar detalles del usuario

MÉTODOS:
  + loadUserByUsername(username: String): Optional<User>
  + loadUserById(userId: Long): Optional<User>
```

**Implementación**:
```
infrastructure/config/security/UserDetailsProviderAdapter.java

RESPONSABILIDADES:
  - Delegar al UserRepository
  - Convertir datos de persistencia a modelos de dominio
```

### Puertos de Salida (Outbound)

#### UserRepository
```
domain/ports/out/UserRepository.java

RESPONSABILIDAD:
  Define el contrato para acceso a persistencia de usuarios

MÉTODOS:
  + save(user: User): User
  + findByUsername(username: String): Optional<User>
  + findById(id: Long): Optional<User>
  + findByEmail(email: String): Optional<User>
  + existsByUsername(username: String): boolean
  + existsByEmail(email: String): boolean
```

**Implementación Principal**:
```
infrastructure/repositories/UserRepositoryAdapter.java

RESPONSABILIDADES:
  - Convertir entre entidades JPA y modelos de dominio
  - Delegar operaciones CRUD al JpaUserRepository
```

**Implementación JPA**:
```
infrastructure/repositories/JpaUserRepository.java

RESPONSABILIDADES:
  - Interfaz JPA para acceso a base de datos
  - Métodos de query específicos
```

## 🔄 Flujo de Datos

### Flujo de Login

```
┌──────────────────┐
│   HTTPClient     │  POST /api/v1/auth/login
└────────┬─────────┘  {"username": "user", "password": "pass"}
         │
         ▼
┌──────────────────────────────────┐
│   AuthController::login()        │  [ADAPTER]
│  - Recibe LoginRequest           │
│  - Llama al LoginUseCase         │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   LoginUseCase (interface)       │  [PUERTO IN]
│  + login(username, password)     │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   LoginUseCaseImpl                │  [USE CASE]
│  - Valida credenciales           │
│  - Llama UserRepository          │
│  - Verifica estado del usuario   │
│  - Retorna User autenticado      │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   UserRepository (interface)     │  [PUERTO OUT]
│  + findByUsername(username)      │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   UserRepositoryAdapter          │  [ADAPTER]
│  - Mapea UserEntity a User       │
│  - Llama JpaUserRepository       │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   JpaUserRepository              │  [JPA]
│  - Consulta base de datos        │
│  - Retorna UserEntity            │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   TokenProvider                  │  [PUERTO IN]
│  + generateToken(user)           │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   JwtTokenProvider               │  [ADAPTER]
│  - Crea JWT con claims           │
│  - Firma con HMAC-SHA512         │
│  - Retorna token string          │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   AuthController                 │  [ADAPTER]
│  - Crea LoginResponse            │
│  - Retorna 200 + token           │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────┐
│   HTTPClient     │  200 OK + JWT Token
└──────────────────┘
```

### Flujo de Autenticación en Solicitud

```
┌──────────────────┐
│   HTTPClient     │  GET /api/v1/event/1
│                  │  Authorization: Bearer <token>
└────────┬─────────┘
         │
         ▼
┌──────────────────────────────────┐
│   JwtAuthenticationFilter        │  [ADAPTER]
│  - Extrae token del header       │
│  - Llama TokenProvider           │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   TokenProvider                  │  [PUERTO IN]
│  + validateToken(token)          │
│  + extractUsername(token)        │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   JwtTokenProvider               │  [ADAPTER]
│  - Valida firma y expiración     │
│  - Extrae username               │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   CustomUserDetailsService       │  [ADAPTER]
│  - Llama UserDetailsProvider     │
│  - Crea UserDetails de Spring    │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   SecurityContextHolder          │  [SPRING]
│  - Establece contexto de auth    │
│  - Continúa con el request       │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────────────────────┐
│   EventController::getById()     │  [ADAPTER]
│  - @PreAuthorize verifica roles  │
│  - Procesa la solicitud          │
└────────┬─────────────────────────┘
         │
         ▼
┌──────────────────┐
│   HTTPClient     │  200 OK + Event Data
└──────────────────┘
```

## 🔐 Control de Acceso

```
┌──────────────────────────────────┐
│   @PreAuthorize("hasRole(X)")   │  [ANNOTATION]
│  - Intercepta la solicitud       │
│  - Verifica rol del usuario      │
└────────┬─────────────────────────┘
         │
         ├─── HAS ROLE? ────────────┐
         │                          │
         ▼                          ▼
    ✅ YES (200)               ❌ NO (403)
    └─ Ejecuta           └─ GlobalExceptionHandler
       método                 ├─ Retorna 403
       endpoint                └─ Error message

ROLES:
  ROLE_USER  - Solo GET
  ROLE_ADMIN - Todos
```

## 📊 Componentes y sus Responsabilidades

### Capa de Dominio (Domain)
| Componente | Responsabilidad |
|-----------|-----------------|
| User | Modelo de usuario (datos + comportamiento) |
| Role | Modelo de rol |
| LoginUseCase | Interfaz para login |
| RegisterUseCase | Interfaz para registro |
| TokenProvider | Interfaz para tokens |
| UserDetailsProvider | Interfaz para cargar usuarios |
| UserRepository | Interfaz para acceso a datos |

**Características**:
- ✅ No depende de frameworks
- ✅ Lógica de negocio pura
- ✅ Interfaces claras

### Capa de Aplicación (Application)
| Componente | Responsabilidad |
|-----------|-----------------|
| LoginUseCaseImpl | Implementa lógica de login |
| RegisterUseCaseImpl | Implementa lógica de registro |

**Características**:
- ✅ Orquestación de puertos
- ✅ Lógica de negocio específica
- ✅ Manejo de excepciones de negocio

### Capa de Infraestructura (Infrastructure)

#### Controllers
| Componente | Responsabilidad |
|-----------|-----------------|
| AuthController | Endpoints de autenticación |

#### Adaptadores de Entrada
| Componente | Responsabilidad |
|-----------|-----------------|
| JwtAuthenticationFilter | Intercepta requests y autentica |
| CustomUserDetailsService | Carga detalles del usuario |

#### Adaptadores de Salida
| Componente | Responsabilidad |
|-----------|-----------------|
| JwtTokenProvider | Implementa TokenProvider |
| UserRepositoryAdapter | Adaptador del repositorio |
| UserDetailsProviderAdapter | Adaptador de carga de usuarios |
| UserMapper | Mapea User <-> UserEntity |
| RoleMapper | Mapea Role <-> RoleEntity |

#### Repositorios JPA
| Componente | Responsabilidad |
|-----------|-----------------|
| JpaUserRepository | Interfaz JPA para usuarios |

#### Configuración
| Componente | Responsabilidad |
|-----------|-----------------|
| SecurityConfig | Configuración de Spring Security |

**Características**:
- ✅ Adaptadores específicos de tecnología
- ✅ Inyección de dependencias
- ✅ Mapeo de datos

## 🎯 Principios SOLID Aplicados

### 1. Single Responsibility Principle (SRP)
```
LoginUseCaseImpl    ────→  Solo valida credenciales
JwtTokenProvider   ────→  Solo genera/valida tokens
UserRepositoryAdapter  →  Solo mapea datos
```

### 2. Open/Closed Principle (OCP)
```
LoginUseCase (interface)
      ↑
      ├─ LoginUseCaseImpl (actual)
      └─ MockLoginUseCase (testing)
      └─ OtherLoginStrategy (future)
```

### 3. Liskov Substitution Principle (LSP)
```
UserRepository (interface)
      ↑
      ├─ UserRepositoryAdapter (JPA)
      └─ UserRepositoryMock (testing)
      
Ambos pueden usarse intercambiablemente
```

### 4. Interface Segregation Principle (ISP)
```
TokenProvider {
  + generateToken()
  + validateToken()
  + extractUsername()
}

❌ NO incluye: updateUser(), deleteRole(), etc
```

### 5. Dependency Inversion Principle (DIP)
```
// INCORRECTO ❌
class LoginUseCaseImpl {
  private JpaUserRepository repo = new JpaUserRepository();
}

// CORRECTO ✅
class LoginUseCaseImpl {
  private UserRepository repo; // Interface
  
  public LoginUseCaseImpl(UserRepository repo) {
    this.repo = repo;
  }
}
```

## 🔄 Inversión de Dependencias

```
Dependencias de alto nivel (casos de uso) →
    Dependen de abstracciones (puertos) →
        Dependen de implementaciones (adaptadores)

FLUJO:
Controller
    ↓ (inyecta)
LoginUseCase (interface/puerto)
    ↓ (inyecta)
LoginUseCaseImpl
    ↓ (inyecta)
UserRepository (interface/puerto)
    ↓ (inyecta)
UserRepositoryAdapter
    ↓ (usa)
JpaUserRepository
    ↓ (accede)
Base de Datos
```

## 🧪 Testabilidad

### Ventajas de la Arquitectura Hexagonal

```
LoginUseCaseImpl {
  UserRepository repo;
  PasswordEncoder encoder;
  
  LoginUseCaseImpl(UserRepository repo, PasswordEncoder encoder) {
    this.repo = repo;
    this.encoder = encoder;
  }
}

// En tests ✅
@Test
void testLogin() {
  UserRepository mockRepo = mock(UserRepository.class);
  PasswordEncoder mockEncoder = mock(PasswordEncoder.class);
  
  LoginUseCase useCase = new LoginUseCaseImpl(mockRepo, mockEncoder);
  // Test sin dependencies reales
}
```

### Inyección de Dependencias
```
@SpringBootTest
class LoginUseCaseTest {
  @MockBean
  UserRepository userRepository;
  
  @Autowired
  LoginUseCase loginUseCase;
  
  @Test
  void shouldLoginWithValidCredentials() {
    // Prueba aislada
  }
}
```

## 📈 Extensibilidad

### Agregar Nuevo Método de Autenticación

```
// Crear nuevo puerto
domain/ports/in/auth/OAuthUseCase.java

// Crear implementación
application/usecases/auth/OAuthUseCaseImpl.java

// Crear adaptador
infrastructure/config/security/OAuthAdapter.java

// Crear controller
infrastructure/web/controller/OAuthController.java

// No se modifica nada existente ✅
```

### Agregar Nueva Persistencia

```
// Mantener UserRepository igual

// Nueva implementación
infrastructure/repositories/UserRepositoryMongoAdapter.java

// Cambiar inyección en SecurityConfig
// El resto del código no se afecta ✅
```

## 🎓 Resumen

La implementación de JWT en esta arquitectura proporciona:

✅ **Separación de responsabilidades** - Cada clase tiene un propósito único  
✅ **Independencia** - Lógica de negocio independiente de frameworks  
✅ **Testabilidad** - Fácil de mockear y testear  
✅ **Flexibilidad** - Cambiar implementaciones sin afectar el código  
✅ **Escalabilidad** - Agregar nuevas funcionalidades fácilmente  
✅ **Mantenibilidad** - Código claro y bien organizado  
✅ **Reutilización** - Puertos y adaptadores reutilizables  
