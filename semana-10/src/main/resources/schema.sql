-- ========================================================
-- Script DDL para MySQL / TechSolutions SAC (Semana 10)
-- Estudiante: Israel Angel Huaman Huaman
-- Repositorio: https://github.com/israelhuamanh/daw-projects
-- ========================================================

-- Parte 1: Creación de las 4 bases de datos independientes
CREATE DATABASE IF NOT EXISTS empleados_dev;
CREATE DATABASE IF NOT EXISTS empleados_qa;
CREATE DATABASE IF NOT EXISTS empleados_test;
CREATE DATABASE IF NOT EXISTS empleados_prod;

-- Tabla Empleado (según requerimiento empresarial)
CREATE TABLE IF NOT EXISTS empleado (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    sueldo DECIMAL(10,2) NOT NULL
);

-- Inserción de datos de prueba base
INSERT INTO empleado (nombres, apellidos, email, sueldo) VALUES 
('Israel Angel', 'Huaman Huaman', 'israel.huaman@techsolutions.com', 4500.00),
('Carlos', 'Mendoza Gomez', 'carlos.mendoza@techsolutions.com', 3200.00),
('Ana', 'Torres Silva', 'ana.torres@techsolutions.com', 3800.00);
