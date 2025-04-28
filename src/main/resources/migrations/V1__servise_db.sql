-- Удаление всех таблиц (если надо)
DROP TABLE IF EXISTS user_role, shift_users, users, product_weight, items_in_item, items, units, product_stock_movement, order_items, cash_register, orders, collections, shift, product_stock, products, categories CASCADE;

-- Создание ENUM'а для user_role
CREATE TYPE user_role_enum AS ENUM ('ACCOUNTANT', 'ADMIN', 'CASHIER');

-- Теперь создание таблиц

CREATE TABLE categories (
                            id BIGSERIAL PRIMARY KEY,
                            categories_name VARCHAR(255),
                            parent_category_id BIGINT,
                            CONSTRAINT fk_parent_category_category FOREIGN KEY (parent_category_id) REFERENCES categories (id)
);

CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255)
);

CREATE TABLE product_stock (
                               id BIGSERIAL PRIMARY KEY,
                               price INT NOT NULL,
                               weight INT NOT NULL,
                               product_id BIGINT,
                               CONSTRAINT fk_stock_products_product FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE shift (
                       id BIGSERIAL PRIMARY KEY,
                       end_date TIMESTAMP(6),
                       start_date TIMESTAMP(6),
                       state_of_shift BOOLEAN NOT NULL
);

CREATE TABLE collections (
                             id BIGSERIAL PRIMARY KEY,
                             date_of_operation TIMESTAMP(6),
                             sum_of_operation INT,
                             type_of_operation BOOLEAN,
                             shift_id BIGINT,
                             CONSTRAINT fk_shift_collections FOREIGN KEY (shift_id) REFERENCES shift (id)
);

CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        cash_less_paid INT NOT NULL,
                        cash_paid INT NOT NULL,
                        date TIMESTAMP(6),
                        establishment_paid INT NOT NULL,
                        number_of_table INT NOT NULL,
                        sum_of_change INT NOT NULL,
                        shift_id BIGINT,
                        CONSTRAINT fk_shift_orders FOREIGN KEY (shift_id) REFERENCES shift (id)
);

CREATE TABLE cash_register (
                               id BIGSERIAL PRIMARY KEY,
                               cash_amount INT NOT NULL,
                               collection_id BIGINT UNIQUE,
                               order_id BIGINT UNIQUE,
                               CONSTRAINT fk_collection_cash_register FOREIGN KEY (collection_id) REFERENCES collections (id),
                               CONSTRAINT fk_order_cash_register FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE order_items (
                             id BIGSERIAL PRIMARY KEY,
                             category VARCHAR(255),
                             date_of_item_change TIMESTAMP(6),
                             discount INT NOT NULL,
                             item BIGINT,
                             name_of_items VARCHAR(255),
                             product_volume VARCHAR(255),
                             price INT NOT NULL,
                             quantity INT NOT NULL,
                             type_of_item BOOLEAN,
                             unit VARCHAR(255),
                             order_id BIGINT,
                             CONSTRAINT fk_order_order_items FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE product_stock_movement (
                                        id BIGSERIAL PRIMARY KEY,
                                        balance_weight INT NOT NULL,
                                        date_of_operation TIMESTAMP(6),
                                        description VARCHAR(255),
                                        price_movement INT NOT NULL,
                                        price_on_stock INT NOT NULL,
                                        product BIGINT,
                                        product_name VARCHAR(255),
                                        type_of_operation VARCHAR(255),
                                        weight INT NOT NULL,
                                        order_items_id BIGINT,
                                        CONSTRAINT fk_order_items_product_stock_movement FOREIGN KEY (order_items_id) REFERENCES order_items (id)
);

CREATE TABLE units (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255)
);

CREATE TABLE items (
                       id BIGSERIAL PRIMARY KEY,
                       date_of_change TIMESTAMP(6),
                       img VARCHAR(255),
                       name_of_items VARCHAR(255),
                       product_volume VARCHAR(255),
                       price INT NOT NULL,
                       type_of_item BOOLEAN,
                       view BOOLEAN,
                       categories_id BIGINT,
                       unit_id BIGINT,
                       CONSTRAINT fk_unit_items FOREIGN KEY (unit_id) REFERENCES units (id),
                       CONSTRAINT fk_categories_items FOREIGN KEY (categories_id) REFERENCES categories (id)
);

CREATE TABLE items_in_item (
                               id BIGSERIAL PRIMARY KEY,
                               quantity INT NOT NULL,
                               item_id BIGINT,
                               parent_item_id BIGINT,
                               CONSTRAINT fk_parent_item_items_in_item FOREIGN KEY (parent_item_id) REFERENCES items (id),
                               CONSTRAINT fk_item_items_in_item FOREIGN KEY (item_id) REFERENCES items (id)
);

CREATE TABLE product_weight (
                                id BIGSERIAL PRIMARY KEY,
                                weight INT NOT NULL,
                                item_id BIGINT,
                                product_id BIGINT,
                                CONSTRAINT fk_product_product_weight FOREIGN KEY (product_id) REFERENCES products (id),
                                CONSTRAINT fk_item_product_weight FOREIGN KEY (item_id) REFERENCES items (id)
);

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       active BOOLEAN NOT NULL,
                       chat_id BIGINT NOT NULL,
                       login VARCHAR(255),
                       name VARCHAR(255),
                       password VARCHAR(255)
);

CREATE TABLE shift_users (
                             shift_id BIGINT NOT NULL,
                             user_id BIGINT NOT NULL,
                             CONSTRAINT fk_shift_user FOREIGN KEY (user_id) REFERENCES users (id),
                             CONSTRAINT fk_user_shift FOREIGN KEY (shift_id) REFERENCES shift (id)
);

CREATE TABLE user_role (
                           user_id BIGINT NOT NULL,
                           roles user_role_enum,
                           CONSTRAINT fk_user_role FOREIGN KEY (user_id) REFERENCES users (id)
);
