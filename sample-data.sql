-- SmartStockAI Sample Data
-- Run this script after the application creates the tables

-- Note: Passwords should be encrypted by the application
-- Use the registration form to create users properly

-- Sample Products
INSERT INTO product (name, sku, cost_price, selling_price, min_stock, created_at, updated_at) VALUES
('Laptop Dell XPS 15', 'DELL-XPS-001', 75000.00, 95000.00, 5, NOW(), NOW()),
('iPhone 14 Pro', 'APPLE-IP14-001', 85000.00, 110000.00, 10, NOW(), NOW()),
('Samsung Galaxy S23', 'SAMSUNG-S23-001', 65000.00, 85000.00, 8, NOW(), NOW()),
('Sony WH-1000XM5 Headphones', 'SONY-WH-001', 18000.00, 25000.00, 15, NOW(), NOW()),
('iPad Air 5th Gen', 'APPLE-IPAD-001', 45000.00, 60000.00, 7, NOW(), NOW()),
('MacBook Pro M2', 'APPLE-MBP-001', 120000.00, 155000.00, 3, NOW(), NOW()),
('Dell UltraSharp Monitor 27"', 'DELL-MON-001', 25000.00, 35000.00, 10, NOW(), NOW()),
('Logitech MX Master 3 Mouse', 'LOGI-MX3-001', 5500.00, 8500.00, 20, NOW(), NOW()),
('Mechanical Keyboard RGB', 'MECH-KB-001', 4000.00, 6500.00, 15, NOW(), NOW()),
('USB-C Hub Multiport', 'USBC-HUB-001', 1500.00, 2500.00, 25, NOW(), NOW());

-- Sample Stock (assuming product IDs 1-10)
INSERT INTO stock (product_id, quantity, last_updated) VALUES
(1, 8, NOW()),
(2, 15, NOW()),
(3, 12, NOW()),
(4, 20, NOW()),
(5, 10, NOW()),
(6, 4, NOW()),
(7, 14, NOW()),
(8, 25, NOW()),
(9, 18, NOW()),
(10, 30, NOW());

-- Sample Sales (assuming user_id 1 and product IDs 1-10)
-- Note: You need to create a user first through the application
-- Then update the user_id in these INSERT statements

-- INSERT INTO sale (product_id, user_id, quantity, total_amount, sale_date) VALUES
-- (1, 1, 2, 190000.00, DATE_SUB(NOW(), INTERVAL 5 DAY)),
-- (2, 1, 3, 330000.00, DATE_SUB(NOW(), INTERVAL 4 DAY)),
-- (4, 1, 5, 125000.00, DATE_SUB(NOW(), INTERVAL 3 DAY)),
-- (8, 1, 10, 85000.00, DATE_SUB(NOW(), INTERVAL 2 DAY)),
-- (9, 1, 7, 45500.00, DATE_SUB(NOW(), INTERVAL 1 DAY)),
-- (10, 1, 15, 37500.00, NOW());

-- Sample Stock Transactions
-- INSERT INTO stock_transaction (product_id, transaction_type, quantity, transaction_date, notes) VALUES
-- (1, 'IN', 10, DATE_SUB(NOW(), INTERVAL 10 DAY), 'Initial stock'),
-- (2, 'IN', 20, DATE_SUB(NOW(), INTERVAL 10 DAY), 'Initial stock'),
-- (3, 'IN', 15, DATE_SUB(NOW(), INTERVAL 10 DAY), 'Initial stock'),
-- (1, 'OUT', 2, DATE_SUB(NOW(), INTERVAL 5 DAY), 'Sale'),
-- (2, 'OUT', 3, DATE_SUB(NOW(), INTERVAL 4 DAY), 'Sale');

-- Query to check data
-- SELECT p.name, p.sku, p.selling_price, s.quantity, p.min_stock,
--        CASE WHEN s.quantity < p.min_stock THEN 'LOW STOCK' ELSE 'OK' END as status
-- FROM product p
-- LEFT JOIN stock s ON p.id = s.product_id
-- ORDER BY p.name;

-- Query to check sales
-- SELECT s.sale_date, p.name, s.quantity, s.total_amount
-- FROM sale s
-- JOIN product p ON s.product_id = p.id
-- ORDER BY s.sale_date DESC;
