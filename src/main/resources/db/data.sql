INSERT INTO product (name, description) VALUES
('Laptop', 'Laptop with intel processor'),
('Smartphone', 'smartphone with lot of ram'),
('Mechanical keyboard', 'with red switches');

INSERT INTO orders (description, status) VALUES
('Juan order', 'PENDING'),
('María order', 'COMPLETED');

INSERT INTO order_product (order_id, product_id) VALUES
(1, 1),
(1, 2),
(2, 3);