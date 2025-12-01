-- ================================
-- V1__init.sql
-- Creación inicial de tablas (PostgreSQL)
-- ================================

-- Tabla Venue
CREATE TABLE venues (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    capacity INTEGER NOT NULL
);

-- Tabla Event
CREATE TABLE events_table (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date TIMESTAMP NOT NULL,
    venue_id BIGINT
);
