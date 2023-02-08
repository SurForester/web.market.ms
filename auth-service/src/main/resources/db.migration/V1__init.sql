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
