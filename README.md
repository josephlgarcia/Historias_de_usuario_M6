# Event Manager - Sistema de Gestión de Eventos

## 📋 Descripción

Event Manager es una aplicación Spring Boot para la gestión de eventos y lugares, con sistema de autenticación JWT y control de acceso basado en roles (RBAC). Implementa arquitectura hexagonal y sigue principios SOLID.

## ✨ Características Principales

- 🔐 **Autenticación JWT** - Segura y escalable
- 👥 **Control de Acceso por Roles** - Admin vs Usuario
- 📚 **Arquitectura Hexagonal** - Limpia y mantenible
- 🧪 **SOLID Principles** - Código de calidad
- 📖 **API RESTful** - Endpoints bien documentados
- 🗄️ **PostgreSQL** - Base de datos robusta
- 📊 **Flyway** - Migración de BD versionada
- 🔄 **MapStruct** - Mapeo de objetos automático
- 📝 **Swagger/OpenAPI** - Documentación interactiva

## 🚀 Quick Start

### Requisitos
- Java 17+
- Maven 3.8+
- PostgreSQL 12+
- Docker (opcional)

### Instalación

1. **Clonar el repositorio**
```bash
git clone <repository-url>
cd Historias_de_usuario_M6
```

2. **Configurar variables de entorno**
```bash
export JWT_SECRET="tuSecretoMuyLargoYSeguroConMinimo256Bits1234567890"
export JWT_EXPIRATION="3600000"
export DB_USER="postgres"
export DB_PASSWORD="tu_contraseña"
export DB_NAME="event_manager"
export SPRING_DATABASE="jdbc:postgresql://localhost"
export DB_PORT="5432"
export SPRING_JPA_DATABASE_PLATFORM="org.hibernate.dialect.PostgreSQLDialect"
export SPRING_JPA_HIBERNATE_DDL_AUTO="validate"
export SPRING_JPA_SHOW_SQL="false"
export FLYWAY_ENABLED="true"
export FLYWAY_BASELINE_ON_MIGRATE="false"
export FLYWAY_LOCATIONS="classpath:db/migration"
export FLYWAY_SCHEMAS="public"
export FLYWAY_VALIDATE_ON_MIGRATE="true"
export APP_NAME="EventManager"
export SERVER_PORT="8080"
```

3. **Iniciar PostgreSQL**
```bash
# Con Docker
docker run -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=event_manager -p 5432:5432 postgres:15

# O crear base de datos manualmente
createdb event_manager
```

4. **Compilar y ejecutar**
```bash
mvn clean compile
mvn spring-boot:run
```

5. **Acceder a la aplicación**
- API: http://localhost:8080/api/v1
- Swagger UI: http://localhost:8080/swagger-ui/index.html

## 📚 Documentación

- **[JWT_IMPLEMENTATION.md](./JWT_IMPLEMENTATION.md)** - Implementación de JWT y autenticación
- **[TESTING_GUIDE.md](./TESTING_GUIDE.md)** - Guía de prueba con ejemplos curl
- **[ARCHITECTURE.md](./ARCHITECTURE.md)** - Explicación de arquitectura hexagonal
- **[CHANGELOG.md](./CHANGELOG.md)** - Resumen de cambios

## 🔐 Autenticación y Autorización

### Usuarios de Prueba

| Usuario | Contraseña | Roles |
|---------|-----------|-------|
| `user` | `password123` | ROLE_USER |
| `admin` | `admin123` | ROLE_USER, ROLE_ADMIN |

### Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user",
    "password": "password123"
  }'
```

### Usar Token
```bash
curl -X GET http://localhost:8080/api/v1/event/1 \
  -H "Authorization: Bearer <token>"
```

## 📊 API Endpoints

### Autenticación
- `POST /api/v1/auth/login` - Login de usuario
- `POST /api/v1/auth/register` - Registro de nuevo usuario

### Eventos
- `GET /api/v1/event` - Listar todos los eventos (USER, ADMIN)
- `GET /api/v1/event/{id}` - Obtener evento por ID (USER, ADMIN)
- `GET /api/v1/event/capacity/{capacity}` - Filtrar por capacidad (USER, ADMIN)
- `POST /api/v1/event` - Crear evento (ADMIN)
- `PUT /api/v1/event/{id}` - Actualizar evento (ADMIN)
- `DELETE /api/v1/event/{id}` - Eliminar evento (ADMIN)

### Lugares
- `GET /api/v1/venue` - Listar todos los lugares (USER, ADMIN)
- `GET /api/v1/venue/{id}` - Obtener lugar por ID (USER, ADMIN)
- `POST /api/v1/venue` - Crear lugar (ADMIN)
- `PUT /api/v1/venue/{id}` - Actualizar lugar (ADMIN)
- `DELETE /api/v1/venue/{id}` - Eliminar lugar (ADMIN)

## 🏗️ Estructura del Proyecto

```
src/main/java/com/events/eventManager/
├── domain/                    # Lógica de negocio
│   ├── model/               # Modelos de dominio
│   └── ports/               # Interfaces (puertos)
├── application/               # Casos de uso
│   └── usecases/            # Implementaciones de casos de uso
└── infrastructure/            # Adaptadores
    ├── config/              # Configuración
    ├── web/                 # Controllers y DTOs
    ├── repositories/        # Acceso a datos
    ├── entities/            # Entidades JPA
    ├── mappers/             # Mapeo de objetos
    └── util/                # Utilidades
```

## 🔧 Configuración

### application.properties
```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/event_manager
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# JWT
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}

# Flyway
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=false
```

## 🐳 Docker

### Construir imagen
```bash
docker build -t event-manager:1.0 .
```

### Ejecutar con Docker Compose
```bash
docker-compose up -d
```

## 🧪 Testing

### Ejecutar pruebas unitarias
```bash
mvn test
```

### Compilar sin tests
```bash
mvn clean compile -DskipTests
```

### Pruebas manuales
Ver [TESTING_GUIDE.md](./TESTING_GUIDE.md)

## 📊 Base de Datos

### Tablas
- `users` - Información del usuario
- `roles` - Roles del sistema
- `user_roles` - Relación usuario-rol
- `events` - Eventos
- `venues` - Lugares

### Migraciones Flyway
```
V1__init.sql              # Tablas base
V2__relations.sql         # Relaciones
V3__adjustment.sql        # Ajustes
V4__user_security.sql     # Usuarios y seguridad
```

## 🔐 Seguridad

### Mejores Prácticas Implementadas
- ✅ Contraseñas encriptadas con BCrypt
- ✅ Tokens JWT con firma HMAC-SHA512
- ✅ Validación de token en cada solicitud
- ✅ Sesiones sin estado (STATELESS)
- ✅ Control de acceso granular por roles
- ✅ Manejo centralizado de excepciones

### Headers de Seguridad
```
Authorization: Bearer <jwt_token>
X-Trace-Id: <trace_id>
Content-Type: application/json
```

## 🛠️ Stack Tecnológico

- **Backend**: Spring Boot 3.3.4
- **Seguridad**: Spring Security 6, JWT (JJWT 0.11.5)
- **BD**: PostgreSQL, Flyway
- **ORM**: JPA/Hibernate
- **Mapeo**: MapStruct 1.5.5
- **Utilidades**: Lombok 1.18.32
- **Documentación**: Swagger/OpenAPI
- **Testing**: JUnit, Mockito

## 📈 Monitoreo y Logs

### Niveles de Log
```properties
logging.level.root=INFO
logging.level.com.events.eventManager=DEBUG
logging.level.org.springframework.security=DEBUG
```

### Trace ID
Cada solicitud tiene un trace ID único para debugging:
```
X-Trace-Id: abc123def456
```

## 🚀 Deployment

### Producción
```bash
mvn clean package -P prod -DskipTests
java -jar target/eventManager-0.0.1-SNAPSHOT.jar
```

### Variables de Entorno (Producción)
```bash
export JWT_SECRET="<algo-muy-seguro-256-bits>"
export JWT_EXPIRATION="7200000"
export DB_USER="prod_user"
export DB_PASSWORD="<contraseña-segura>"
export SPRING_PROFILES_ACTIVE="prod"
```

## 📝 Convenciones de Código

### Nombres de Paquetes
- `domain` - Lógica pura de negocio
- `application` - Casos de uso
- `infrastructure` - Adaptadores específicos

### Nombres de Clases
- `*UseCase` - Interfaz de caso de uso
- `*UseCaseImpl` - Implementación de caso de uso
- `*Adapter` - Adaptador (patrón)
- `*Controller` - Controlador REST
- `*Entity` - Entidad JPA
- `*Request` / `*Response` - DTO
- `*Mapper` - Mapper de objetos

## 🤝 Contribuir

1. Fork el proyecto
2. Crear rama de feature (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver archivo `LICENSE` para más detalles.

## 📞 Soporte

Para preguntas o problemas:
1. Revisar la documentación en el proyecto
2. Consultar logs con el trace ID
3. Ver TESTING_GUIDE.md para problemas comunes

## 👥 Autores

- **José Luis García** - Desarrollo inicial

## 🔄 Changelog

Ver [CHANGELOG.md](./CHANGELOG.md) para historial de cambios.

---

**Estado**: ✅ Producción Listo  
**Última Actualización**: 2024-12-02  
**Versión**: 1.0.0