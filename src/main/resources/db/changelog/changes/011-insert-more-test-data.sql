-- liquibase formatted sql

-- changeset mightyloot:011-insert-comprehensive-test-data context:dev
-- ==========================================
-- 6. СТВОРЕННЯ ТОВАРІВ (Деталей/Інструментів)
-- 50 тестових елементів для перевірки пагінації та UI
-- ==========================================
INSERT INTO items (id, sku, name, workspace_id, unit_of_measure, category_id, package_type, min_stock, location_id, parameters, type, total_quantity, note) VALUES
-- Існуючі базові товари
(60, 'IC-LM317', 'Регулятор напруги LM317T', 1, 'PCS', 2, 'TO-220', 5, 3, '{"voltage_out": "1.2V - 37V", "max_current": "1.5A"}'::jsonb, 'REGULAR', 15, 'Для лабораторного блоку живлення'),
(61, 'TOOL-UNI', 'Мультиметр UNI-T UT61E', 1, 'PCS', 5, NULL, 1, 4, '{"accuracy": "True RMS", "auto_range": true}'::jsonb, 'UNIQUE', 1, 'Основний робочий мультиметр'),
(62, 'WIRE-CU', 'Мідний дріт 0.2мм', 1, 'METERS', 1, 'Котушка', 10, 2, '{"diameter": "0.2mm", "material": "Copper"}'::jsonb, 'REGULAR', 50.5, 'Для намотування котушок'),

-- Резистори (Категорія 3)
(63, 'RES-10K', 'Резистор 10k Ом 0.25W', 1, 'PCS', 3, 'THT', 50, 3, '{"tolerance": "1%", "power": "0.25W"}'::jsonb, 'REGULAR', 120, 'Підтягуючі резистори'),
(64, 'RES-1K', 'Резистор 1k Ом 0.25W', 1, 'PCS', 3, 'THT', 50, 3, '{"tolerance": "1%", "power": "0.25W"}'::jsonb, 'REGULAR', 85, 'Для світлодіодів'),
(65, 'RES-220R', 'Резистор 220 Ом 0.25W', 1, 'PCS', 3, 'THT', 50, 3, '{"tolerance": "5%", "power": "0.25W"}'::jsonb, 'REGULAR', 200, NULL),
(66, 'RES-4K7', 'Резистор 4.7k Ом 0.25W', 1, 'PCS', 3, 'THT', 50, 3, '{"tolerance": "1%", "power": "0.25W"}'::jsonb, 'REGULAR', 45, 'Для I2C шини'),
(67, 'RES-100K', 'Резистор 100k Ом 0.25W', 1, 'PCS', 3, 'THT', 20, 3, '{"tolerance": "5%", "power": "0.25W"}'::jsonb, 'REGULAR', 30, NULL),
(68, 'RES-SMD-10K', 'SMD Резистор 10k Ом', 1, 'PCS', 3, '0805', 100, 3, '{"tolerance": "1%", "size": "0805"}'::jsonb, 'REGULAR', 450, 'Стрічка'),
(69, 'RES-SMD-1K', 'SMD Резистор 1k Ом', 1, 'PCS', 3, '0805', 100, 3, '{"tolerance": "1%", "size": "0805"}'::jsonb, 'REGULAR', 320, 'Стрічка'),
(11, 'RES-SMD-330R', 'SMD Резистор 330 Ом', 1, 'PCS', 3, '0603', 100, 3, '{"tolerance": "1%", "size": "0603"}'::jsonb, 'REGULAR', 500, 'Стрічка'),

-- Мікросхеми та Мікроконтролери (Категорія 2)
(12, 'MCU-ESP32', 'Мікроконтролер ESP32-WROOM-32', 1, 'PCS', 2, 'SMD', 5, 2, '{"wifi": true, "bluetooth": true, "cores": 2}'::jsonb, 'REGULAR', 12, 'Для IoT проектів'),
(13, 'MCU-ESP8266', 'Модуль NodeMCU v3 ESP8266', 1, 'PCS', 2, 'Board', 2, 2, '{"wifi": true, "usb_chip": "CH340"}'::jsonb, 'REGULAR', 4, NULL),
(14, 'MCU-AT328', 'ATmega328P-PU', 1, 'PCS', 2, 'DIP-28', 10, 3, '{"flash": "32KB", "clock": "20MHz"}'::jsonb, 'REGULAR', 15, 'Для саморобних Arduino'),
(15, 'IC-NE555', 'Таймер NE555P', 1, 'PCS', 2, 'DIP-8', 10, 3, '{"voltage": "4.5V-15V"}'::jsonb, 'REGULAR', 25, 'Генератор імпульсів'),
(16, 'IC-74HC595', 'Зсувний регістр 74HC595', 1, 'PCS', 2, 'DIP-16', 10, 3, '{"type": "8-bit shift register"}'::jsonb, 'REGULAR', 18, 'Для розширення пінів'),
(17, 'IC-L293D', 'Драйвер двигунів L293D', 1, 'PCS', 2, 'DIP-16', 5, 3, '{"channels": 2, "current": "0.6A"}'::jsonb, 'REGULAR', 8, 'Для робототехніки'),
(18, 'MCU-OPI5', 'Orange Pi 5 16GB', 1, 'PCS', 2, 'SBC', 1, 1, '{"ram": "16GB", "cpu": "RK3588S"}'::jsonb, 'UNIQUE', 1, 'Основа для Cyberdeck проекту'),
(19, 'MCU-RPI4', 'Raspberry Pi 4 8GB', 1, 'PCS', 2, 'SBC', 1, 1, '{"ram": "8GB", "cpu": "BCM2711"}'::jsonb, 'UNIQUE', 2, 'Сервер Home Assistant'),
(20, 'MCU-CM5', 'Orange Pi CM5', 1, 'PCS', 2, 'Module', 1, 2, '{"compatible": "Raspberry Pi CM4"}'::jsonb, 'REGULAR', 1, 'Для кастомної плати'),

-- Вимірювальні прилади та Радіо (Категорія 5)
(21, 'RF-RTL', 'RTL-SDR Blog V4', 1, 'PCS', 5, 'Dongle', 1, 4, '{"freq_range": "500kHz-1.7GHz", "adc": "8-bit"}'::jsonb, 'UNIQUE', 1, 'Слухати радіоефір'),
(22, 'RF-VNA', 'NanoVNA-H4', 1, 'PCS', 5, 'Device', 1, 4, '{"screen": "4 inch", "freq_max": "1.5GHz"}'::jsonb, 'UNIQUE', 1, 'Аналізатор антен'),
(23, 'TOOL-OSC', 'Осцилограф Hantek 2D15', 1, 'PCS', 5, 'Device', 1, 4, '{"bandwidth": "150MHz", "channels": 2}'::jsonb, 'UNIQUE', 1, 'Настільний осцилограф'),

-- Інструменти (Категорія 4)
(24, 'TOOL-SOLD', 'Паяльник Pinecil V2', 1, 'PCS', 4, NULL, 1, 4, '{"power": "65W", "protocol": "PD/QC"}'::jsonb, 'UNIQUE', 1, 'Портативний паяльник'),
(25, 'TOOL-STRIP', 'Стрипер для проводів Knipex', 1, 'PCS', 4, NULL, 1, 4, '{"awg": "10-24"}'::jsonb, 'UNIQUE', 1, 'Автоматичний знімач ізоляції'),
(26, 'TOOL-CRIMP', 'Кримпер SN-28B', 1, 'PCS', 4, NULL, 1, 4, '{"terminals": "Dupont, JST-XH"}'::jsonb, 'UNIQUE', 1, 'Для обтискання конекторів'),
(27, 'TOOL-TWEEZ', 'Пінцет антистатичний ESD-15', 1, 'PCS', 4, NULL, 1, 4, '{"tip": "Curved"}'::jsonb, 'REGULAR', 2, NULL),
(28, 'TOOL-MAT', 'Силіконовий килимок для пайки', 1, 'PCS', 4, NULL, 1, 4, '{"size": "40x30cm", "heat_res": "500C"}'::jsonb, 'UNIQUE', 1, 'На робочому столі'),

-- Компоненти, Радіо, Мережі та Різне (Категорія 1)
(29, 'NET-MIKRO', 'MikroTik hAP ax2', 1, 'PCS', 1, 'Router', 1, 1, '{"wifi": "Wi-Fi 6", "ports": "5x Gigabit"}'::jsonb, 'UNIQUE', 1, 'Експерименти з EoIP та WireGuard'),
(30, 'NET-SW', 'Комутатор 8-port Gigabit', 1, 'PCS', 1, 'Device', 1, 1, '{"managed": false}'::jsonb, 'REGULAR', 1, 'Для розширення мережі'),
(31, 'LED-RED-5', 'Світлодіод Червоний 5мм', 1, 'PCS', 1, 'THT', 50, 3, '{"color": "Red", "vf": "2.0V"}'::jsonb, 'REGULAR', 150, NULL),
(32, 'LED-GRN-5', 'Світлодіод Зелений 5мм', 1, 'PCS', 1, 'THT', 50, 3, '{"color": "Green", "vf": "3.0V"}'::jsonb, 'REGULAR', 140, NULL),
(33, 'LED-BLU-5', 'Світлодіод Синій 5мм', 1, 'PCS', 1, 'THT', 50, 3, '{"color": "Blue", "vf": "3.2V"}'::jsonb, 'REGULAR', 120, NULL),
(34, 'CAP-100UF', 'Конденсатор 100uF 16V', 1, 'PCS', 1, 'Radial', 20, 3, '{"type": "Electrolytic"}'::jsonb, 'REGULAR', 45, NULL),
(35, 'CAP-1000UF', 'Конденсатор 1000uF 25V', 1, 'PCS', 1, 'Radial', 10, 3, '{"type": "Electrolytic"}'::jsonb, 'REGULAR', 22, 'Для фільтрації живлення'),
(36, 'CAP-104', 'Конденсатор 100nF (104)', 1, 'PCS', 1, 'Ceramic', 100, 3, '{"type": "Ceramic", "voltage": "50V"}'::jsonb, 'REGULAR', 300, 'Шунтуючі конденсатори'),
(37, 'DIO-1N4148', 'Діод 1N4148', 1, 'PCS', 1, 'DO-35', 50, 3, '{"type": "Switching"}'::jsonb, 'REGULAR', 180, NULL),
(38, 'DIO-1N4007', 'Діод 1N4007', 1, 'PCS', 1, 'DO-41', 50, 3, '{"type": "Rectifier", "voltage": "1000V", "current": "1A"}'::jsonb, 'REGULAR', 90, 'Випрямний діод'),
(39, 'TR-2N2222', 'Транзистор 2N2222', 1, 'PCS', 1, 'TO-92', 20, 3, '{"type": "NPN"}'::jsonb, 'REGULAR', 60, NULL),
(40, 'TR-IRFZ44N', 'Транзистор IRFZ44N', 1, 'PCS', 1, 'TO-220', 10, 3, '{"type": "N-Channel MOSFET"}'::jsonb, 'REGULAR', 15, 'Для силових навантажень'),
(41, 'REL-5V', 'Модуль реле 5V 1-канал', 1, 'PCS', 1, 'Module', 5, 2, '{"trigger": "Low level"}'::jsonb, 'REGULAR', 8, 'Для Arduino'),
(42, 'CON-SMA-F', 'Конектор SMA Female на плату', 1, 'PCS', 1, 'PCB Edge', 10, 3, '{"impedance": "50 Ohm"}'::jsonb, 'REGULAR', 24, 'Для радіомодулів'),
(43, 'CON-BNC-P', 'Конектор BNC Male', 1, 'PCS', 1, 'Cable', 5, 3, '{"cable_type": "RG58"}'::jsonb, 'REGULAR', 10, 'Для антен 136-174 МГц'),
(44, 'WIRE-RG58', 'Кабель коаксіальний RG58', 1, 'METERS', 1, 'Котушка', 5, 2, '{"impedance": "50 Ohm"}'::jsonb, 'REGULAR', 12.5, 'Антенний кабель'),
(45, 'MOD-CC1101', 'Радіомодуль CC1101 433MHz', 1, 'PCS', 1, 'Module', 2, 2, '{"freq": "433MHz", "interface": "SPI"}'::jsonb, 'REGULAR', 3, 'Субгігагерцові експерименти'),
(46, 'MOD-NRF24', 'Радіомодуль nRF24L01+', 1, 'PCS', 1, 'Module', 5, 2, '{"freq": "2.4GHz"}'::jsonb, 'REGULAR', 7, NULL),
(47, 'BAT-18650', 'Акумулятор Li-Ion 18650', 1, 'PCS', 1, '18650', 4, 2, '{"capacity": "3200mAh", "brand": "Panasonic"}'::jsonb, 'REGULAR', 6, 'Для портативного живлення'),
(48, 'MOD-TP4056', 'Модуль заряду TP4056 Type-C', 1, 'PCS', 1, 'Module', 10, 2, '{"input": "5V Type-C", "current": "1A"}'::jsonb, 'REGULAR', 14, 'Для заряду 18650'),
(49, 'BREADBOARD', 'Макетна плата MB-102', 1, 'PCS', 1, 'Board', 2, 4, '{"points": 830}'::jsonb, 'REGULAR', 3, 'Для прототипування схем'),
(50, 'WIRE-DUPONT', 'Перемички Dupont M-M 20см', 1, 'PACK', 1, 'Bundle', 2, 2, '{"wires": 40}'::jsonb, 'REGULAR', 5, 'Для макетної плати');