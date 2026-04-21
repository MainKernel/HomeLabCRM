-- liquibase formatted sql

-- changeset mightyloot:010-insert-comprehensive-test-data context:dev
-- Опис: Комплексне наповнення бази тестовими даними для розробки

-- ==========================================
-- 1. СТВОРЕННЯ РОБОЧИХ ПРОСТОРІВ
-- ==========================================
INSERT INTO workspace (id, name) VALUES
(1, 'Домашня майстерня'),
(2, 'Гараж');

-- ==========================================
-- 2. СТВОРЕННЯ КОРИСТУВАЧІВ
-- Пароль для обох користувачів: password123
-- (Це валідний BCrypt хеш для цього пароля)
-- ==========================================
INSERT INTO users (id, email, password_hash, role) VALUES
(1, 'admin@homelab.com', '$2a$10$xYFSDENqiBDhmzI3Nug2EOpxHo8bJQgcZPLRloFRKuApQVyOILp1m', 'ROLE_ADMIN'),
(2, 'ivan@homelab.com', '$2a$10$xYFSDENqiBDhmzI3Nug2EOpxHo8bJQgcZPLRloFRKuApQVyOILp1m', 'ROLE_USER');

-- ==========================================
-- 3. ПРИЗНАЧЕННЯ ДОСТУПІВ ДО ПРОСТОРІВ (Багато-до-Багатьох)
-- ==========================================
INSERT INTO user_workspaces (user_id, workspace_id, workspace_role) VALUES
(1, 1, 'OWNER'),  -- Адмін володіє майстернею
(1, 2, 'OWNER'),  -- Адмін володіє гаражем
(2, 1, 'MEMBER'); -- Іван має доступ лише до майстерні як учасник

-- ==========================================
-- 4. СТВОРЕННЯ КАТЕГОРІЙ (Ієрархія)
-- ==========================================
INSERT INTO categories (id, name, parent_id) VALUES
(1, 'Електронні компоненти', NULL),
(2, 'Мікросхеми', 1),
(3, 'Резистори', 1),
(4, 'Інструменти', NULL),
(5, 'Вимірювальні прилади', 4);

-- ==========================================
-- 5. СТВОРЕННЯ ЛОКАЦІЙ (Ієрархія)
-- ==========================================
INSERT INTO locations (id, name, workspace_id, description, parent_id) VALUES
(1, 'Стелаж біля вікна', 1, 'Головний стелаж в кімнаті', NULL),
(2, 'Полиця 1', 1, 'Верхня полиця для дрібниць', 1),
(3, 'Каса з компонентами', 1, 'Пластикові прозорі шухлядки', 2),
(4, 'Робочий стіл', 1, 'Зона активної пайки', NULL);

-- ==========================================
-- 6. СТВОРЕННЯ ТОВАРІВ (Деталей/Інструментів)
-- ==========================================
INSERT INTO items (id, sku, name, workspace_id, unit_of_measure, category_id, package_type, min_stock, location_id, parameters, type, total_quantity, note) VALUES
(1, 'IC-LM317', 'Регулятор напруги LM317T', 1, 'PCS', 2, 'TO-220', 5, 3, '{"voltage_out": "1.2V - 37V", "max_current": "1.5A"}'::jsonb, 'REGULAR', 15, 'Для лабораторного блоку живлення'),
(2, 'TOOL-UNI', 'Мультиметр UNI-T UT61E', 1, 'PCS', 5, NULL, 1, 4, '{"accuracy": "True RMS", "auto_range": true}'::jsonb, 'UNIQUE', 1, 'Основний робочий мультиметр'),
(3, 'WIRE-CU', 'Мідний дріт 0.2мм', 1, 'METERS', 1, 'Котушка', 10, 2, '{"diameter": "0.2mm", "material": "Copper"}'::jsonb, 'REGULAR', 50.5, 'Для намотування котушок радіостанції');

-- ==========================================
-- 7. СТВОРЕННЯ ЛОГІВ (Історія інвентаризації)
-- ==========================================
INSERT INTO inventory_logs (item_id, change_type, quantity_changed, unit_price, total_price, note, created_at) VALUES
(1, 'IN', 15, 12.50, 187.50, 'Початкова закупівля компонентів', CURRENT_TIMESTAMP),
(2, 'IN', 1, 2500.00, 2500.00, 'Куплено на радіоринку', CURRENT_TIMESTAMP),
(3, 'IN', 50.5, 5.00, 252.50, 'Залишки від старого проєкту', CURRENT_TIMESTAMP);

-- ==========================================
-- 8. ДОДАВАННЯ ДОКУМЕНТІВ ДО ТОВАРІВ
-- ==========================================
INSERT INTO item_documents (item_id, title, file_url, document_type, uploaded_at) VALUES
(1, 'LM317 Datasheet', 'https://www.ti.com/lit/ds/symlink/lm317.pdf', 'DATASHEET', CURRENT_TIMESTAMP),
(2, 'Інструкція UNI-T', 'https://example.com/manual.pdf', 'MANUAL', CURRENT_TIMESTAMP);

-- ==========================================
-- 9. ВАЖЛИВО: ОНОВЛЕННЯ ПОСЛІДОВНОСТЕЙ (SEQUENCES)
-- ==========================================
-- Оскільки ми жорстко задали ID (1, 2, 3...), PostgreSQL може не знати про це
-- і наступного разу при створенні через API спробує видати ID = 1, що викличе помилку.
-- Ці команди "переводять годинник" бази даних вперед.
SELECT setval('workspace_id_seq', (SELECT MAX(id) FROM workspace));
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('categories_id_seq', (SELECT MAX(id) FROM categories));
SELECT setval('locations_id_seq', (SELECT MAX(id) FROM locations));
SELECT setval('items_id_seq', (SELECT MAX(id) FROM items));
SELECT setval('inventory_logs_id_seq', (SELECT MAX(id) FROM inventory_logs));
SELECT setval('item_documents_id_seq', (SELECT MAX(id) FROM item_documents));

-- rollback DELETE FROM workspace;
-- rollback DELETE FROM users;
-- (Видаляти інші немає сенсу, бо стоїть ON DELETE CASCADE на ключах)