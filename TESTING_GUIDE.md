# Guía de Prueba - JWT y Control de Acceso

## 🚀 Preparación

### 1. Configurar Variables de Entorno
```bash
export JWT_SECRET="tuSecretoMuyLargoYSeguroConMinimo256Bits1234567890"
export JWT_EXPIRATION="3600000"
export DB_USER="postgres"
export DB_PASSWORD="tu_contraseña"
export DB_NAME="event_manager"
```

### 2. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

### 3. Acceder a Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

## 👤 Usuarios de Prueba

### Usuario Estándar
- **Username**: user
- **Password**: password123
- **Roles**: ROLE_USER
- **Acceso**: Solo GET (lectura)

### Administrador
- **Username**: admin
- **Password**: admin123
- **Roles**: ROLE_USER, ROLE_ADMIN
- **Acceso**: Completo (GET, POST, PUT, DELETE)

## 📝 Flujo de Prueba

### 1️⃣ LOGIN CON USUARIO ESTÁNDAR

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user",
    "password": "password123"
  }'
```

**Respuesta Esperada (200)**:
```json
{
  "data": {
    "token": "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "userId": 1,
    "username": "user",
    "email": "user@eventmanager.com",
    "expiresIn": 3600000
  },
  "message": "Login exitoso",
  "success": true
}
```

**Guardar el Token**:
```bash
USER_TOKEN="eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9..."
```

### 2️⃣ ACCEDER A EVENTO (Como USER)

**GET - PERMITIDO** ✅
```bash
curl -X GET http://localhost:8080/api/v1/event/1 \
  -H "Authorization: Bearer $USER_TOKEN"
```

**Respuesta Esperada (200)**:
```json
{
  "data": {
    "id": 1,
    "name": "Evento de Prueba",
    ...
  },
  "message": "Event retrieved successfully",
  "success": true
}
```

**POST - PROHIBIDO** ❌
```bash
curl -X POST http://localhost:8080/api/v1/event \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Nuevo Evento",
    ...
  }'
```

**Respuesta Esperada (403)**:
```json
{
  "timestamp": "2024-12-02T10:30:00.123456Z",
  "status": 403,
  "error": "Forbidden",
  "message": "Access Denied",
  "path": "/api/v1/event",
  "traceId": "abc123..."
}
```

### 3️⃣ LOGIN COMO ADMIN

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Guardar el Token**:
```bash
ADMIN_TOKEN="eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9..."
```

### 4️⃣ CREAR EVENTO (Como ADMIN)

**POST - PERMITIDO** ✅
```bash
curl -X POST http://localhost:8080/api/v1/event \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Evento Administrativo",
    "description": "Creado por admin",
    "date": "2024-12-15",
    "venueId": 1
  }'
```

**Respuesta Esperada (200)**:
```json
{
  "data": {
    "id": 2,
    "name": "Evento Administrativo",
    ...
  },
  "message": "Event created successfully",
  "success": true
}
```

### 5️⃣ ACTUALIZAR EVENTO (Como ADMIN)

**PUT - PERMITIDO** ✅
```bash
curl -X PUT http://localhost:8080/api/v1/event/1 \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Evento Actualizado",
    "description": "Modificado por admin",
    "date": "2024-12-20",
    "venueId": 1
  }'
```

### 6️⃣ ELIMINAR EVENTO (Como ADMIN)

**DELETE - PERMITIDO** ✅
```bash
curl -X DELETE http://localhost:8080/api/v1/event/2 \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

**Respuesta Esperada (204)**:
```
No Content
```

### 7️⃣ REGISTRO DE NUEVO USUARIO

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "newuser",
    "email": "newuser@example.com",
    "password": "securePassword123!",
    "firstName": "Nuevo",
    "lastName": "Usuario"
  }'
```

**Respuesta Esperada (200)**:
```json
{
  "data": {
    "id": 3,
    "username": "newuser",
    "email": "newuser@example.com",
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

## 🔍 Casos de Error

### 1. Credenciales Inválidas

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user",
    "password": "wrongpassword"
  }'
```

**Respuesta (401)**:
```json
{
  "timestamp": "2024-12-02T10:30:00.123456Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Usuario o contraseña inválidos",
  "path": "/api/v1/auth/login",
  "traceId": "xyz789..."
}
```

### 2. Token Expirado

```bash
curl -X GET http://localhost:8080/api/v1/event/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.expired..."
```

**Respuesta (401)**:
```json
{
  "timestamp": "2024-12-02T10:30:00.123456Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Token JWT inválido o expirado",
  "path": "/api/v1/event/1",
  "traceId": "expired123..."
}
```

### 3. Sin Token

```bash
curl -X POST http://localhost:8080/api/v1/event \
  -H "Content-Type: application/json" \
  -d '{...}'
```

**Respuesta (403)**:
```json
{
  "timestamp": "2024-12-02T10:30:00.123456Z",
  "status": 403,
  "error": "Forbidden",
  "message": "Access Denied",
  "path": "/api/v1/event",
  "traceId": "no_token123..."
}
```

### 4. Usuario/Email Duplicado en Registro

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user",
    "email": "nuevo@example.com",
    "password": "password123",
    "firstName": "Duplicate",
    "lastName": "User"
  }'
```

**Respuesta (409)**:
```json
{
  "timestamp": "2024-12-02T10:30:00.123456Z",
  "status": 409,
  "error": "Conflict",
  "message": "El nombre de usuario ya existe",
  "path": "/api/v1/auth/register",
  "traceId": "duplicate123..."
}
```

## 📊 Matriz de Permisos

| Método | Endpoint | USER | ADMIN |
|--------|----------|------|-------|
| GET | /api/v1/event | ✅ | ✅ |
| GET | /api/v1/event/{id} | ✅ | ✅ |
| GET | /api/v1/event/capacity/{capacity} | ✅ | ✅ |
| POST | /api/v1/event | ❌ | ✅ |
| PUT | /api/v1/event/{id} | ❌ | ✅ |
| DELETE | /api/v1/event/{id} | ❌ | ✅ |
| GET | /api/v1/venue | ✅ | ✅ |
| GET | /api/v1/venue/{id} | ✅ | ✅ |
| POST | /api/v1/venue | ❌ | ✅ |
| PUT | /api/v1/venue/{id} | ❌ | ✅ |
| DELETE | /api/v1/venue/{id} | ❌ | ✅ |
| POST | /api/v1/auth/login | ✅ | ✅ |
| POST | /api/v1/auth/register | ✅ | ✅ |

## 🐛 Troubleshooting

### Token no Válido
- Verificar que el token está correctamente copiado
- Revisar que no hay espacios al inicio/final
- Confirmar que el JWT_SECRET configurado es el correcto

### 401 Unauthorized en Endpoints Públicos
- Los endpoints de `/api/v1/auth/**` son públicos
- Los otros endpoints requieren un token válido

### 403 Forbidden
- Verificar el rol del usuario
- USER solo puede hacer GET
- ADMIN puede hacer cualquier operación

### Usuario Bloqueado/Deshabilitado
- Verificar que `enabled = true` en la base de datos
- Verificar que `account_non_locked = true`
- Verificar que `credentials_non_expired = true`

## 🔐 Validación de Token en JWT.io

1. Ir a https://jwt.io
2. Copiar el token completo en la sección "Encoded"
3. Verificar:
   - Header: `{"alg":"HS512","typ":"JWT"}`
   - Payload contiene username, userId, email, roles
   - Signature se verifica con el JWT_SECRET

## 📋 Script de Prueba Completa

```bash
#!/bin/bash

API_URL="http://localhost:8080"

echo "🔐 1. Testing LOGIN con USER..."
USER_RESPONSE=$(curl -s -X POST $API_URL/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user", "password":"password123"}')

USER_TOKEN=$(echo $USER_RESPONSE | jq -r '.data.token')
echo "✅ Token USER: ${USER_TOKEN:0:20}..."

echo "📖 2. Testing GET Event con USER (debe funcionar)..."
curl -s -X GET $API_URL/api/v1/event/1 \
  -H "Authorization: Bearer $USER_TOKEN" | jq .

echo "❌ 3. Testing POST Event con USER (debe fallar)..."
curl -s -X POST $API_URL/api/v1/event \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"Test"}' | jq .

echo "🔐 4. Testing LOGIN con ADMIN..."
ADMIN_RESPONSE=$(curl -s -X POST $API_URL/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin", "password":"admin123"}')

ADMIN_TOKEN=$(echo $ADMIN_RESPONSE | jq -r '.data.token')
echo "✅ Token ADMIN: ${ADMIN_TOKEN:0:20}..."

echo "✅ 5. Testing POST Event con ADMIN (debe funcionar)..."
curl -s -X POST $API_URL/api/v1/event \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{...}' | jq .

echo "✅ Pruebas completadas!"
```

## 📚 Información Adicional

### Headers Importantes
- `Authorization`: Bearer token
- `Content-Type`: application/json

### Códigos de Estado HTTP
- 200: OK (solicitud exitosa)
- 201: Created (recurso creado)
- 204: No Content (sin contenido)
- 400: Bad Request (datos inválidos)
- 401: Unauthorized (autenticación fallida)
- 403: Forbidden (sin permiso)
- 404: Not Found (recurso no existe)
- 409: Conflict (duplicado)
- 500: Internal Server Error (error del servidor)

### Trace ID
Cada respuesta contiene un `traceId` para debugging:
```bash
curl -s http://localhost:8080/api/v1/event/1 \
  -H "X-Trace-Id: mi-trace-123" | jq .
```
