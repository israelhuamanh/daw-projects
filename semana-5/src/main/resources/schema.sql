-- ========================================================
-- Script DDL para MySQL / Base de datos: laboratorio_api (Semana 5)
-- Estudiante: Israel Angel Huaman Huaman
-- Repositorio: https://github.com/israelhuamanh/daw-projects
-- ========================================================

CREATE DATABASE IF NOT EXISTS laboratorio_api;
USE laboratorio_api;

CREATE TABLE IF NOT EXISTS producto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    categoria VARCHAR(100) NOT NULL
);
