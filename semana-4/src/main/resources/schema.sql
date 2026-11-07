-- ========================================================
-- Script DDL para MySQL / Base de datos: laboratorio_api
-- Estudiante: Israel Angel Huaman Huaman
-- Repositorio: https://github.com/israelhuamanh/daw-projects
-- ========================================================

CREATE DATABASE IF NOT EXISTS laboratorio_api;
USE laboratorio_api;

-- 1. Tablas para Relación 1:N (Categoría y Producto)
CREATE TABLE IF NOT EXISTS categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    precio DOUBLE NOT NULL,
    categoria_id BIGINT,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE CASCADE
);

-- 2. Tablas para Relación 1:1 (Usuario y Perfil)
CREATE TABLE IF NOT EXISTS perfil (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(150) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    perfil_id BIGINT UNIQUE,
    CONSTRAINT fk_usuario_perfil FOREIGN KEY (perfil_id) REFERENCES perfil(id) ON DELETE CASCADE
);

-- 3. Tablas para Relación N:M (Estudiante y Curso)
CREATE TABLE IF NOT EXISTS curso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    creditos INT NOT NULL
);

CREATE TABLE IF NOT EXISTS estudiante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS estudiante_curso (
    estudiante_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    PRIMARY KEY (estudiante_id, curso_id),
    CONSTRAINT fk_estudiante_curso_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiante(id) ON DELETE CASCADE,
    CONSTRAINT fk_estudiante_curso_curso FOREIGN KEY (curso_id) REFERENCES curso(id) ON DELETE CASCADE
);
