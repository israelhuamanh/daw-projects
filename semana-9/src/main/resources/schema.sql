-- ========================================================
-- Script DDL para MySQL / Base de datos: security_db (Semana 9)
-- Estudiante: Israel Angel Huaman Huaman
-- Repositorio: https://github.com/israelhuamanh/daw-projects
-- ========================================================

CREATE DATABASE IF NOT EXISTS security_db;
USE security_db;

-- 1. Tabla roles
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- 2. Tabla users
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- 3. Tabla intermedia user_roles
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

-- 4. Inserción de Roles Base
INSERT IGNORE INTO roles (id, name) VALUES (1, 'USER');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'MODERATOR');
INSERT IGNORE INTO roles (id, name) VALUES (3, 'ADMIN');
