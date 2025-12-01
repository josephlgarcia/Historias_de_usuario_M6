-- ================================
-- V2__relaciones.sql
-- Relaciones, llaves foráneas e índices
-- ================================

-- Llave foránea: events_table.venue_id → venues.id
ALTER TABLE events_table
ADD CONSTRAINT fk_event_venue
FOREIGN KEY (venue_id)
REFERENCES venues(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- Índice para mejorar búsquedas por venue_id
CREATE INDEX idx_event_venue
    ON events_table (venue_id);
