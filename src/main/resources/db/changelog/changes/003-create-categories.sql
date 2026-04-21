-- liquibase formatted sql

-- changeset mightyloot:003-create-categories
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    parent_id BIGINT,
    CONSTRAINT fk_category_parent FOREIGN KEY (parent_id) REFERENCES categories (id) ON DELETE SET NULL
);

CREATE TABLE workspace (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- changeset mightyloot:004-create-items-table
CREATE TABLE items (
    id BIGSERIAL PRIMARY KEY,
    sku VARCHAR(100),                               -- 1. Артикул
    name VARCHAR(255) NOT NULL,                     -- 2. Назва
    image_url VARCHAR(500),                         -- 3. Картинка (шлях до файлу/S3)
    workspace_id BIGINT NOT NULL,                   -- 3.1. ID складу
    unit_of_measure VARCHAR(20) NOT NULL,           -- 4. Кількість (шт, м, г)
    category_id BIGINT,                             -- 5. Категорія
    package_type VARCHAR(100),                      -- 6. Типорозмір (THT, SMD 0805)
    min_stock NUMERIC(38,2) NOT NULL DEFAULT 0,           -- 7. Мінімальна кількість
    location_id BIGINT NOT NULL,                    -- 8. Місцезнаходження
    parameters JSONB,                               -- 9. Параметри
    serial_number VARCHAR(255),                     -- 10. Серійний номер (якщо UNIQUE)
    note TEXT,                                      -- 13. Примітка

    type VARCHAR(50) NOT NULL,                      -- REGULAR або UNIQUE
    total_quantity NUMERIC(38,2) NOT NULL DEFAULT 0, -- Загальна кількість зараз

    CONSTRAINT fk_item_location FOREIGN KEY (location_id) REFERENCES locations (id) ON DELETE RESTRICT,
    CONSTRAINT fk_item_category FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE SET NULL
);

CREATE INDEX idx_items_category_id ON items(category_id);
CREATE INDEX idx_items_parameters ON items USING GIN (parameters);

-- changeset mightyloot:005-create-item-documents
CREATE TABLE item_documents (                       -- 11. Документи
    id BIGSERIAL PRIMARY KEY,
    item_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    file_url VARCHAR(500) NOT NULL,
    file_uuid VARCHAR(500) NOT NULL,
    document_type VARCHAR(50),                      -- DATASHEET, MANUAL, INVOICE
    uploaded_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_document_item FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE
);

-- changeset mightyloot:006-create-inventory-logs
CREATE TABLE inventory_logs (                       -- 12. Ціна та 14. Історія змін
    id BIGSERIAL PRIMARY KEY,
    item_id BIGINT NOT NULL,
    change_type VARCHAR(50) NOT NULL,               -- IN (прихід), OUT (витрата), AUDIT (корекція)
    quantity_changed NUMERIC(10, 2) NOT NULL,       -- Скільки додано/віднято
    unit_price NUMERIC(10, 2),                      -- Ціна за одиницю в цій партії
    total_price NUMERIC(10, 2),                     -- Загальна вартість партії
    note TEXT,                                      -- Коментар до транзакції (наприклад, "Купив на AliExpress")
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_log_item FOREIGN KEY (item_id) REFERENCES items (id) ON DELETE CASCADE
);