
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE roles IS 'Roles del sistema para control de acceso';
COMMENT ON COLUMN roles.id IS 'Identificador único del rol';
COMMENT ON COLUMN roles.name IS 'Nombre del rol (ej: ROLE_USER, ROLE_ADMIN)';
COMMENT ON COLUMN roles.description IS 'Descripción del propósito del rol';

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    enabled BOOLEAN NOT NULL DEFAULT true,
    account_non_expired BOOLEAN NOT NULL DEFAULT true,
    account_non_locked BOOLEAN NOT NULL DEFAULT true,
    credentials_non_expired BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE users IS 'Usuarios del sistema con credenciales de acceso';
COMMENT ON COLUMN users.id IS 'Identificador único del usuario';
COMMENT ON COLUMN users.username IS 'Nombre de usuario único para login';
COMMENT ON COLUMN users.email IS 'Correo electrónico único';
COMMENT ON COLUMN users.password IS 'Contraseña encriptada con BCrypt';
COMMENT ON COLUMN users.enabled IS 'Indica si la cuenta está habilitada';
COMMENT ON COLUMN users.account_non_expired IS 'Indica si la cuenta no ha expirado';
COMMENT ON COLUMN users.account_non_locked IS 'Indica si la cuenta no está bloqueada';
COMMENT ON COLUMN users.credentials_non_expired IS 'Indica si las credenciales no han expirado';

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) 
        REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) 
        REFERENCES roles(id) ON DELETE CASCADE
);

COMMENT ON TABLE user_roles IS 'Relación muchos a muchos entre usuarios y roles';

CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_enabled ON users(enabled);
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_user_roles_role_id ON user_roles(role_id);

INSERT INTO roles (name, description) VALUES
    ('ROLE_USER', 'Usuario estándar con acceso de solo lectura'),
    ('ROLE_ADMIN', 'Administrador con acceso completo al sistema');


INSERT INTO users (username, email, password, first_name, last_name) VALUES
    ('user', 'user@eventmanager.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Usuario', 'Estándar');

INSERT INTO users (username, email, password, first_name, last_name) VALUES
    ('admin', 'admin@eventmanager.com', '$2a$10$DOwnh2./8gzj4cVgBPCPz.QhJ1KqK3PmPvDGYCQwmrJXH/PImLYE6', 'Administrador', 'Sistema');

INSERT INTO user_roles (user_id, role_id) VALUES
    (1, 1),
    (2, 1),
    (2, 2);
