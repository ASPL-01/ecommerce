use productstest;
TRUNCATE TABLE products;
INSERT INTO products (name, stock_number, description, rating, list_price, discount, quantity, restricted) VALUES ('Vive', 'HTCV123', 'VR System', 5, 799.99, .10, 100, false);