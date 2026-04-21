-- liquibase formatted sql

-- changeset mightyloot:002-create-locations-table
CREATE TABLE locations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    workspace_id  BIGINT NOT NULL,
    description TEXT,
    parent_id BIGINT,
    CONSTRAINT fk_location_parent FOREIGN KEY (parent_id) REFERENCES locations (id) ON DELETE SET NULL
);

-- Додаємо індекс для швидкого пошуку дочірніх локацій
CREATE INDEX idx_locations_parent_id ON locations(parent_id);

-- rollback DROP INDEX idx_locations_parent_id;
-- rollback DROP TABLE locations;