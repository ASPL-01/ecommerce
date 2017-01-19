use productstest;
TRUNCATE TABLE products;
INSERT INTO products (name, stock_number, description, review, rating, list_price, discount, quantity, restricted) VALUES ('Vive', 'HTCV123', 'VR System', 'Best headset ever', 5.00, 799.99, .10, 100, false);
INSERT INTO products (name, stock_number, description, review, rating, list_price, discount, quantity, restricted) VALUES ('iPhone 7', 'IPHONE1234', 'Smart Phone', 'I like it', 4.00, 599.99, .05, 200, false);
INSERT INTO products (name, stock_number, description, review, rating, list_price, discount, quantity, restricted) VALUES ('Shotgun', 'SHOT123', 'Big Gun', 'I aint never shot somethin like this before', 3.00, 99.99, .00, 500, true);