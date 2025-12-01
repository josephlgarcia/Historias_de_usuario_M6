-- ================================
-- V3__ajustes.sql
-- Ajustes adicionales del módulo (PostgreSQL)
-- ================================

-- Evitar nombres duplicados de Venue
ALTER TABLE venues
ADD CONSTRAINT uq_venue_name UNIQUE (name);

-- Evitar eventos duplicados por nombre y fecha
ALTER TABLE events_table
ADD CONSTRAINT uq_event_name_date UNIQUE (name, date);

-- Validar capacidad > 0
ALTER TABLE venues
ADD CONSTRAINT chk_capacity_positive CHECK (capacity > 0);

-- ==========================================
-- Validación de fecha futura para eventos
-- PostgreSQL REQUIERE usar TRIGGER
-- ==========================================

-- 1. Crear función que valide la fecha futura
CREATE OR REPLACE FUNCTION validate_future_event_date()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.date <= NOW() THEN
        RAISE EXCEPTION 'Event date (%) must be in the future', NEW.date;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 2. Crear trigger que llame a la función
CREATE TRIGGER trg_validate_future_event_date
BEFORE INSERT OR UPDATE ON events_table
FOR EACH ROW
EXECUTE FUNCTION validate_future_event_date();
