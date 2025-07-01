-- CATEGORIES
INSERT INTO categories (id, name) VALUES
(1, 'Poké Balls'),
(2, 'Potions'),
(3, 'TMs & HMs'),
(4, 'Accessories');

-- PRODUCTS
INSERT INTO products (id, name, price, image_url, featured, rating, review_count, category_id) VALUES
(1, 'Ultra Ball', 1200.00, 'https://example.com/images/ultraball.png', true, 4.7, 35, 1),
(2, 'Max Potion', 2500.00, 'https://example.com/images/maxpotion.png', false, 4.9, 22, 2),
(3, 'TM Thunderbolt', 3000.00, 'https://example.com/images/thunderbolt.png', true, 4.5, 40, 3),
(4, 'Pokémon Hat - Pikachu', 1800.00, 'https://example.com/images/pikachuhat.png', false, 4.0, 10, 4);

-- USERS
INSERT INTO users (id, username, email, password) VALUES
(1, 'ashketchum', 'ash@pokemon.com', 'pikachu123'),
(2, 'misty', 'misty@cerulean.com', 'togepi456'),
(3, 'brock', 'brock@pewter.com', 'onix789');

-- REVIEWS
INSERT INTO reviews (id, rating, body, product_id, user_id) VALUES
(1, 5.0, 'Ultra Ball never fails!', 1, 1),
(2, 4.0, 'Potion is good but pricey.', 2, 2),
(3, 5.0, 'Thunderbolt TM is OP!', 3, 3),
(4, 3.5, 'Hat is cute, a bit small.', 4, 1);

-- CARTS
INSERT INTO carts (id, total_price, user_id) VALUES
(1, 5400.00, 1);

-- CART ITEMS (in your model, these might be embedded – adapt as needed)
INSERT INTO cart_items (id, product_id, name, price, quantity, product_ref_id, cart_id) VALUES
(1, 1, 'Ultra Ball', 1200.00, 2, 1, 1),
(2, 3, 'TM Thunderbolt', 3000.00, 1, 3, 1);
