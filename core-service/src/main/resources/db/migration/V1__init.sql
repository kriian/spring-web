CREATE TABLE IF NOT EXISTS products
(
    id    BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    price INT
);

INSERT INTO products (title, price)
VALUES ('Milk', 100),
       ('Bread', 80),
       ('Cheese', 90);

CREATE TABLE orders
(
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(255) NOT NULL,
    total_price INT          NOT NULL,
    address     VARCHAR,
    phone       VARCHAR(255),
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);

CREATE TABLE order_items
(
    id                BIGSERIAL PRIMARY KEY,
    product_id        BIGINT NOT NULL REFERENCES products (id),
    quantity          INT    NOT NULL,
    order_id          BIGINT NOT NULL REFERENCES orders (id),
    price_per_product INT    NOT NULL,
    price             INT    NOT NULL,
    created_at        TIMESTAMP,
    updated_at        TIMESTAMP
);

INSERT INTO orders (username, total_price, address, phone)
VALUES ('bob', 200, 'address', '12345');

INSERT INTO order_items (product_id, order_id, quantity, price_per_product, price)
VALUES (1, 1, 2, 100, 200);