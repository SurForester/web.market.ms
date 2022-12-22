DROP SEQUENCE IF EXISTS users_id_seq;
CREATE SEQUENCE IF NOT EXISTS users_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
ALTER SEQUENCE users_id_seq OWNER TO postgres;
DROP TABLE IF EXISTS users;
CREATE TABLE users (
                       id                    bigint NOT NULL DEFAULT nextval('users_id_seq'),
                       username              VARCHAR(50) NOT NULL,
                       password              VARCHAR(256) NOT NULL,
                       first_name            VARCHAR(50) NOT NULL,
                       last_name             VARCHAR(50) NOT NULL,
                       email                 VARCHAR(50) NOT NULL,
                       phone                 VARCHAR(16) NOT NULL,
                       PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS users OWNER to postgres;
/* перевел в формат Постгри, набор полей стандартный для базовой регистрации */

DROP SEQUENCE IF EXISTS roles_id_seq;
CREATE SEQUENCE IF NOT EXISTS roles_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
ALTER SEQUENCE roles_id_seq OWNER TO postgres;
DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
                       id                    bigint NOT NULL DEFAULT nextval('roles_id_seq'),
                       name                  VARCHAR(50) NOT NULL,
                       PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS roles OWNER to postgres;
/* перевел в формат Постгри, набор полей стандартный для базовой регистрации */


DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles (
                             user_id               bigint NOT NULL,
                             role_id               bigint NOT NULL,

                             PRIMARY KEY (user_id,role_id),

                             CONSTRAINT FK_USER_ROLE_ID FOREIGN KEY (user_id) REFERENCES users (id)
                                 ON DELETE NO ACTION ON UPDATE NO ACTION,

                             CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id) REFERENCES roles (id)
                                 ON DELETE NO ACTION ON UPDATE NO ACTION
);
ALTER TABLE IF EXISTS users_roles OWNER to postgres;
/* перевел в формат Постгри, набор полей стандартный для базовой регистрации */

DROP SEQUENCE IF EXISTS categories_id_seq;
CREATE SEQUENCE IF NOT EXISTS categories_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
ALTER SEQUENCE categories_id_seq OWNER TO postgres;
DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
                            id	                  bigint NOT NULL DEFAULT nextval('categories_id_seq'),
                            title                 VARCHAR(255) NOT NULL,
                            description           VARCHAR(5000),
                            PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS categories OWNER to postgres;
/* перевел в формат Постгри, набор полей стандартный для базовой регистрации */

DROP SEQUENCE IF EXISTS products_id_seq;
CREATE SEQUENCE IF NOT EXISTS products_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
ALTER SEQUENCE products_id_seq OWNER TO postgres;
DROP TABLE IF EXISTS products;
CREATE TABLE products (
                          id	                bigint NOT NULL DEFAULT nextval('products_id_seq'),
                          category_id           bigint NOT NULL,
                          vendor_code           VARCHAR(12) NOT NULL,
                          title                 VARCHAR(255) NOT NULL,
                          short_description     VARCHAR(1000) NOT NULL,
                          full_description      VARCHAR(5000) NOT NULL,
                          price                 DECIMAL(8,2) NOT NULL,
                          create_at             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          update_at             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          PRIMARY KEY (id),
                          CONSTRAINT FK_CATEGORY_ID FOREIGN KEY (category_id)
                              REFERENCES categories (id)
);
ALTER TABLE IF EXISTS products OWNER to postgres;
/* перевел в формат Постгри, набор полей стандартный для базовой регистрации */

DROP SEQUENCE IF EXISTS products_img_id_seq;
CREATE SEQUENCE IF NOT EXISTS products_img_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
ALTER SEQUENCE products_img_id_seq OWNER TO postgres;
DROP TABLE IF EXISTS products_images;
CREATE TABLE products_images (
                                 id                    bigint NOT NULL DEFAULT nextval('products_img_id_seq'),
                                 product_id            bigint NOT NULL,
                                 path                  VARCHAR(250) NOT NULL,
                                 PRIMARY KEY (id),
                                 CONSTRAINT FK_PRODUCT_ID_IMG FOREIGN KEY (product_id)
                                     REFERENCES products (id)
);
ALTER TABLE IF EXISTS products_images OWNER to postgres;
/* перевел в формат Постгри, набор полей стандартный для базовой регистрации */

DROP SEQUENCE IF EXISTS order_status_id_seq;
CREATE SEQUENCE IF NOT EXISTS order_status_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
ALTER SEQUENCE order_status_id_seq OWNER TO postgres;
DROP TABLE IF EXISTS orders_statuses;
CREATE TABLE orders_statuses (
                                 id                    bigint NOT NULL DEFAULT nextval('order_status_id_seq'),
                                 title                 VARCHAR(50) DEFAULT NULL,
                                 PRIMARY KEY (id)
);
ALTER TABLE IF EXISTS orders_statuses OWNER to postgres;
/* перевел в формат Постгри, набор полей стандартный для базовой регистрации */

DROP SEQUENCE IF EXISTS address_id_seq;
CREATE SEQUENCE IF NOT EXISTS address_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
ALTER SEQUENCE address_id_seq OWNER TO postgres;
DROP TABLE IF EXISTS delivery_addresses;
CREATE TABLE delivery_addresses (
                                    id	                  bigint NOT NULL DEFAULT nextval('address_id_seq'),
                                    user_id               bigint NOT NULL,
                                    address               VARCHAR(500) NOT NULL,
                                    PRIMARY KEY (id),
                                    CONSTRAINT FK_USER_ID_DEL_ADR FOREIGN KEY (user_id)
                                        REFERENCES users (id)
);
ALTER TABLE IF EXISTS delivery_addresses OWNER to postgres;
/* перевел в формат Постгри, набор полей стандартный для базовой регистрации */

DROP SEQUENCE IF EXISTS order_id_seq;
CREATE SEQUENCE IF NOT EXISTS order_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
ALTER SEQUENCE order_id_seq OWNER TO postgres;
DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
                        id	                  bigint NOT NULL DEFAULT nextval('order_id_seq'),
                        user_id               bigint NOT NULL,
                        status_id             bigint NOT NULL,
                        delivery_address_id   bigint NOT NULL,
                        price                 DECIMAL(12,2) NOT NULL,
                        delivery_price        DECIMAL(12,2) NOT NULL,
                        delivery_date         TIMESTAMP NOT NULL,
                        create_at             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        update_at             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        PRIMARY KEY (id),
                        CONSTRAINT FK_USER_ORDER_ID FOREIGN KEY (user_id) REFERENCES users (id),
                        CONSTRAINT FK_STATUS_ID FOREIGN KEY (status_id) REFERENCES orders_statuses (id),
                        CONSTRAINT FK_DELIVERY_ADDRESS_ID FOREIGN KEY (delivery_address_id) REFERENCES delivery_addresses (id)
);
ALTER TABLE IF EXISTS orders OWNER to postgres;
/* перевел в формат Постгри, набор полей стандартный для базовой регистрации
   убрал номер телефона - он есть в пользователе
   перенес в начало все ссылочные поля*/

DROP SEQUENCE IF EXISTS order_item_id_seq;
CREATE SEQUENCE IF NOT EXISTS order_item_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
ALTER SEQUENCE order_item_id_seq OWNER TO postgres;
DROP TABLE IF EXISTS orders_item;
CREATE TABLE orders_item (
                             id	                   bigint NOT NULL DEFAULT nextval('order_item_id_seq'),
                             product_id            bigint NOT NULL,
                             order_id              bigint NOT NULL,
                             quantity              INT NOT NULL,
                             item_price            DECIMAL(9,2) NOT NULL,
                             total_price           DECIMAL(9,2) NOT NULL,
                             PRIMARY KEY (id),
                             CONSTRAINT FK_ORDER_ID FOREIGN KEY (order_id)
                                 REFERENCES orders (id),
                             CONSTRAINT FK_PRODUCT_ID_ORD_IT FOREIGN KEY (product_id)
                                 REFERENCES products (id)
);
ALTER TABLE IF EXISTS orders_item OWNER to postgres;
/* перевел в формат Постгри, набор полей стандартный для базовой регистрации */


INSERT INTO roles (name)
VALUES
    ('ROLE_ADMIN'), ('ROLE_MANAGER'), ('ROLE_USER');

INSERT INTO users (username,password,first_name,last_name,email,phone)
VALUES
    ('admin','$2a$12$gvLyPP5haD1MEw4Vv5npLewjAyzT/L6l9K5bT.dQ978YXAt.SccI6','Admin','Admin','admin@gmail.com','+79881111111');
/* pass - admin */

INSERT INTO users_roles (user_id, role_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3);

INSERT INTO categories (title)
VALUES
    ('Телевизоры'), ('Ноутбуки');

INSERT INTO orders_statuses (title)
VALUES
    ('Сформирован');

INSERT INTO products (category_id, vendor_code, title, short_description, full_description, price)
VALUES
    (1, '00000001', '20\" Телевизор Samsung UE20NU7170U', 'Коротко: Хороший телевизор Samsung 20', 'LED телевизор Samsung 20', 1.00),
    (1, '00000002', '24\" Телевизор Samsung UE24NU7170U', 'Коротко: Хороший телевизор Samsung 24', 'LED телевизор Samsung 24', 2.00),
    (1, '00000003', '28\" Телевизор Samsung UE28NU7170U', 'Коротко: Хороший телевизор Samsung 28', 'LED телевизор Samsung 28', 3.00),
    (1, '00000004', '32\" Телевизор Samsung UE32NU7170U', 'Коротко: Хороший телевизор Samsung 32', 'LED телевизор Samsung 32', 4.00),
    (1, '00000005', '36\" Телевизор Samsung UE36NU7170U', 'Коротко: Хороший телевизор Samsung 36', 'LED телевизор Samsung 36', 5.00),
    (1, '00000006', '40\" Телевизор Samsung UE40NU7170U', 'Коротко: Хороший телевизор Samsung 40', 'LED телевизор Samsung 40', 6.00),
    (1, '00000007', '44\" Телевизор Samsung UE44NU7170U', 'Коротко: Хороший телевизор Samsung 44', 'LED телевизор Samsung 44', 7.00),
    (1, '00000008', '48\" Телевизор Samsung UE48NU7170U', 'Коротко: Хороший телевизор Samsung 48', 'LED телевизор Samsung 48', 8.00),
    (1, '00000009', '52\" Телевизор Samsung UE52NU7170U', 'Коротко: Хороший телевизор Samsung 52', 'LED телевизор Samsung 52', 9.00),
    (1, '00000010', '56\" Телевизор Samsung UE56NU7170U', 'Коротко: Хороший телевизор Samsung 56', 'LED телевизор Samsung 56', 10.00),
    (1, '00000011', '60\" Телевизор Samsung UE60NU7170U', 'Коротко: Хороший телевизор Samsung 60', 'LED телевизор Samsung 60', 11.00),
    (1, '00000012', '64\" Телевизор Samsung UE64NU7170U', 'Коротко: Хороший телевизор Samsung 64', 'LED телевизор Samsung 64', 12.00);

INSERT INTO products_images (product_id, path)
VALUES
    (2, '2.jpg');

INSERT INTO delivery_addresses (user_id, address)
VALUES
    (1, '18a Diagon Alley'),
    (1, '4 Privet Drive');