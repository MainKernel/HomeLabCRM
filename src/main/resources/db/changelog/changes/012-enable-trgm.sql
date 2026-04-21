-- liquibase formatted sql

--changeset mightyloot:012-enable-trmg
CREATE EXTENSION IF NOT EXISTS pg_trgm;